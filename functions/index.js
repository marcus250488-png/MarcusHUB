
/**
 * Proxy de mercado — token brapi só no servidor (process.env.BRAPI_API_TOKEN).
 * Deploy: firebase functions:config:set brapi.token="..."
 * Ou variável de ambiente BRAPI_API_TOKEN no runtime.
 *
 * Android: BuildConfig.MARKET_API_BASE = https://REGION-PROJECT.cloudfunctions.net/
 * GET /marketQuote?ticker=PETR4
 * GET /marketSearch?q=petro
 */
const functions = require("firebase-functions");
const https = require("https");

function getBrapiToken() {
  try {
    const cfg = functions.config();
    if (cfg && cfg.brapi && cfg.brapi.token) return cfg.brapi.token;
  } catch (_) {}
  return process.env.BRAPI_API_TOKEN || "";
}

function brapiGet(path, token) {
  return new Promise((resolve, reject) => {
    const req = https.request(
      {
        hostname: "brapi.dev",
        path,
        method: "GET",
        headers: {
          Accept: "application/json",
          ...(token ? { Authorization: `Bearer ${token}` } : {})
        },
        timeout: 15000
      },
      (res) => {
        let body = "";
        res.on("data", (c) => (body += c));
        res.on("end", () => {
          resolve({ status: res.statusCode || 500, body });
        });
      }
    );
    req.on("error", reject);
    req.on("timeout", () => {
      req.destroy();
      reject(new Error("timeout"));
    });
    req.end();
  });
}

exports.health = functions.https.onRequest((req, res) => {
  res.status(200).send("B3 Insights functions OK — market proxy + billing Play Store");
});

exports.marketQuote = functions.https.onRequest(async (req, res) => {
  res.set("Access-Control-Allow-Origin", "*");
  if (req.method === "OPTIONS") {
    res.set("Access-Control-Allow-Methods", "GET");
    res.status(204).send("");
    return;
  }
  const ticker = String(req.query.ticker || "").trim().toUpperCase();
  if (!ticker) {
    res.status(400).json({ error: "ticker obrigatório", status: "UNAVAILABLE" });
    return;
  }
  const token = getBrapiToken();
  if (!token) {
    res.status(503).json({
      error: "BRAPI_API_TOKEN não configurado no backend",
      status: "UNAVAILABLE",
      hint: "firebase functions:config:set brapi.token=SEU_TOKEN"
    });
    return;
  }
  try {
    const path = `/api/quote/${encodeURIComponent(ticker)}?range=1d&interval=1d&fundamental=true`;
    const { status, body } = await brapiGet(path, token);
    res.status(status).type("application/json").send(body);
  } catch (e) {
    res.status(502).json({ error: "Falha ao consultar brapi", detail: String(e.message || e) });
  }
});

exports.marketSearch = functions.https.onRequest(async (req, res) => {
  res.set("Access-Control-Allow-Origin", "*");
  if (req.method === "OPTIONS") {
    res.set("Access-Control-Allow-Methods", "GET");
    res.status(204).send("");
    return;
  }
  const q = String(req.query.q || "").trim();
  if (!q) {
    res.status(400).json({ error: "q obrigatório" });
    return;
  }
  const token = getBrapiToken();
  if (!token) {
    res.status(503).json({ error: "BRAPI_API_TOKEN não configurado no backend" });
    return;
  }
  try {
    const path = `/api/quote/list?search=${encodeURIComponent(q)}&limit=15`;
    const { status, body } = await brapiGet(path, token);
    res.status(status).type("application/json").send(body);
  } catch (e) {
    res.status(502).json({ error: "Falha ao consultar brapi", detail: String(e.message || e) });
  }
});
