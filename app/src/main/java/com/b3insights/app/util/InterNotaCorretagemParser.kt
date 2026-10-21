package com.b3insights.app.util

import com.b3insights.app.BuildConfig

import android.content.Context
import android.net.Uri
import android.util.Log
import com.b3insights.app.data.model.AssetType
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import java.util.Calendar
import java.util.Locale

data class BrokerageOperation(
    val ticker: String,
    val assetType: String,
    val quantity: Double,
    val price: Double,
    val total: Double,
    val side: String,
    val purchaseDate: Long,
    val titleRaw: String
)

object InterNotaCorretagemParser {

    private const val TAG = "InterNotaParser"
    private var pdfBoxReady = false

    private fun ensurePdfBox(context: Context) {
        if (!pdfBoxReady) {
            PDFBoxResourceLoader.init(context.applicationContext)
            pdfBoxReady = true
        }
    }

    fun parse(context: Context, uri: Uri): Result<List<BrokerageOperation>> {
        return try {
            ensurePdfBox(context)
            val text = context.contentResolver.openInputStream(uri)?.use { input ->
                PDDocument.load(input).use { doc ->
                    val stripper = PDFTextStripper()
                    stripper.sortByPosition = true
                    stripper.getText(doc)
                }
            } ?: return Result.failure(IllegalArgumentException("Não foi possível abrir o PDF"))
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "PDF text length=${text.length}")
                // sample omitido em release (pode conter CPF/nome)
            }
            val ops = parseText(text)
            Log.d(TAG, "ops found=${ops.size}")
            Result.success(ops)
        } catch (e: Exception) {
            Log.e(TAG, "parse error", e)
            Result.failure(e)
        }
    }

    fun parseText(raw: String): List<BrokerageOperation> {
        // Normaliza espaços e quebras (PdfBox costuma fragmentar mais que pypdf)
        val text = raw
            .replace('\u00a0', ' ')
            .replace('\r', '\n')
            .replace(Regex("[ \\t]+"), " ")
            .replace(Regex("\n{2,}"), "\n")
        val upper = text.uppercase(Locale.ROOT)
        val dateMs = extractPregaoDate(text) ?: System.currentTimeMillis()

        val ops = mutableListOf<BrokerageOperation>()

        // ── 1) Linhas / blocos que citam Bovespa ──────────────────────────
        // Cada ocorrência de "Bovespa" até a próxima (ou 120 chars)
        val bovespaIdx = Regex("""BOVESPA""", RegexOption.IGNORE_CASE).findAll(text).map { it.range.first }.toList()
        for ((i, start) in bovespaIdx.withIndex()) {
            val end = bovespaIdx.getOrNull(i + 1) ?: (start + 120).coerceAtMost(text.length)
            val block = text.substring(start, end.coerceAtMost(text.length))
            parseBlock(block, dateMs)?.let { ops.add(it) }
        }

        // ── 2) Varredura global: títulos conhecidos + números próximos ────
        if (ops.isEmpty()) {
            for ((name, ticker, type) in knownAssets) {
                val idx = upper.indexOf(name)
                if (idx < 0) continue
                val window = text.substring(
                    (idx - 120).coerceAtLeast(0),
                    (idx + name.length + 80).coerceAtMost(text.length)
                )
                val nums = extractQtyPriceTotal(window) ?: continue
                val side = detectSide(window)
                ops.add(
                    BrokerageOperation(
                        ticker = ticker,
                        assetType = type,
                        quantity = nums.first,
                        price = nums.second,
                        total = nums.third,
                        side = side,
                        purchaseDate = dateMs,
                        titleRaw = name
                    )
                )
            }
        }

        // ── 3) Fallback: qualquer trio qtd/preço/valor com D ou C ─────────
        if (ops.isEmpty()) {
            val allNums = extractAllQtyPriceTotal(text)
            for ((qty, price, total, dc, around) in allNums) {
                val mapped = mapTitleInChunk(around) ?: continue
                ops.add(
                    BrokerageOperation(
                        ticker = mapped.first,
                        assetType = mapped.second,
                        quantity = qty,
                        price = price,
                        total = total,
                        side = if (dc.equals("C", true)) "SELL" else "BUY",
                        purchaseDate = dateMs,
                        titleRaw = mapped.third
                    )
                )
            }
        }

        // ── 4) Último recurso: só nomes + qualquer par de números na linha ─
        if (ops.isEmpty()) {
            for ((name, ticker, type) in knownAssets) {
                if (!upper.contains(name)) continue
                val idx = upper.indexOf(name)
                val window = text.substring(
                    (idx - 150).coerceAtLeast(0),
                    (idx + 80).coerceAtMost(text.length)
                )
                // Aceita 2 números (qtd e preço) mesmo sem D/C colado
                val loose = Regex("""(\d{1,6})\s+(\d{1,6}[.,]\d{2})""").findAll(window)
                    .mapNotNull { m ->
                        val q = m.groupValues[1].toDoubleOrNull() ?: return@mapNotNull null
                        val p = m.groupValues[2].brl() ?: return@mapNotNull null
                        if (q > 0 && p > 0) q to p else null
                    }
                    .firstOrNull() ?: continue
                ops.add(
                    BrokerageOperation(
                        ticker = ticker,
                        assetType = type,
                        quantity = loose.first,
                        price = loose.second,
                        total = loose.first * loose.second,
                        side = "BUY",
                        purchaseDate = dateMs,
                        titleRaw = name
                    )
                )
            }
        }

        return ops.distinctBy { "${it.ticker}|${it.quantity}|${it.price}|${it.side}" }
    }

    private fun parseBlock(block: String, dateMs: Long): BrokerageOperation? {
        val mapped = mapTitleInChunk(block) ?: return null
        val nums = extractQtyPriceTotal(block) ?: return null
        return BrokerageOperation(
            ticker = mapped.first,
            assetType = mapped.second,
            quantity = nums.first,
            price = nums.second,
            total = nums.third,
            side = detectSide(block),
            purchaseDate = dateMs,
            titleRaw = mapped.third
        )
    }

    /** Lista (nome na nota → ticker, tipo). Nomes em MAIÚSCULO. */
    private val knownAssets: List<Triple<String, String, String>> = listOf(
        Triple("FII MAXI REN", "MXRF11", AssetType.FII.name),
        Triple("MAXI RENDA", "MXRF11", AssetType.FII.name),
        Triple("MAXI REN", "MXRF11", AssetType.FII.name),
        Triple("KLABIN S/A", "KLBN4", AssetType.ACAO.name),
        Triple("KLABIN S.A", "KLBN4", AssetType.ACAO.name),
        Triple("KLABIN", "KLBN4", AssetType.ACAO.name),
        Triple("PETROBRAS", "PETR4", AssetType.ACAO.name),
        Triple("VALE S/A", "VALE3", AssetType.ACAO.name),
        Triple("VALE S.A", "VALE3", AssetType.ACAO.name),
        Triple("ITAU UNIBANCO", "ITUB4", AssetType.ACAO.name),
        Triple("ITAÚ UNIBANCO", "ITUB4", AssetType.ACAO.name),
        Triple("BRADESCO", "BBDC4", AssetType.ACAO.name),
        Triple("BANCO DO BRASIL", "BBAS3", AssetType.ACAO.name),
        Triple("AMBEV", "ABEV3", AssetType.ACAO.name),
        Triple("WEG S/A", "WEGE3", AssetType.ACAO.name),
        Triple("MXRF11", "MXRF11", AssetType.FII.name),
        Triple("KLBN4", "KLBN4", AssetType.ACAO.name),
        Triple("KLBN3", "KLBN3", AssetType.ACAO.name),
        Triple("PETR4", "PETR4", AssetType.ACAO.name),
        Triple("VALE3", "VALE3", AssetType.ACAO.name)
    )

    /** ticker, assetType, rawName */
    private fun mapTitleInChunk(chunk: String): Triple<String, String, String>? {
        val u = chunk.uppercase(Locale.ROOT)
        // Klabin ON vs PN
        if (u.contains("KLABIN")) {
            val ticker = if (Regex("""\bON\b""").containsMatchIn(u) && !u.contains("PN")) "KLBN3" else "KLBN4"
            return Triple(ticker, AssetType.ACAO.name, "KLABIN S/A")
        }
        for ((name, ticker, type) in knownAssets) {
            if (u.contains(name)) return Triple(ticker, type, name)
        }
        // FII ABCD genérico → ABCD11
        Regex("""FII\s+([A-Z]{4})""").find(u)?.groupValues?.getOrNull(1)?.let { code ->
            return Triple("${code}11", AssetType.FII.name, "FII $code")
        }
        // Ticker explícito
        Regex("""\b([A-Z]{4}\d{1,2})\b""").find(u)?.groupValues?.getOrNull(1)?.let { t ->
            val type = when {
                t.endsWith("11") && t.take(4) in listOf("BOVA", "SMAL", "IVVB", "HASH", "GOLD", "NASD", "PIBB", "XFIX") ->
                    AssetType.ETF.name
                t.endsWith("11") -> AssetType.FII.name
                else -> AssetType.ACAO.name
            }
            return Triple(t, type, t)
        }
        return null
    }

    private fun extractQtyPriceTotal(window: String): Triple<Double, Double, Double>? {
        // Inter: "2 9,18 18,36 D" ou "2 9,18 18,36 DCI"
        val re = Regex("""(\d{1,6})\s+(\d{1,6}[.,]\d{2})\s+(\d{1,8}[.,]\d{2})\s*[DCdc]?""")
        for (m in re.findAll(window)) {
            val q = m.groupValues[1].toDoubleOrNull() ?: continue
            val p = m.groupValues[2].brl() ?: continue
            val tot = m.groupValues[3].brl() ?: (q * p)
            if (q > 0 && p > 0 && tot > 0) return Triple(q, p, tot)
        }
        // Só qtd + preço
        val re2 = Regex("""(\d{1,6})\s+(\d{1,6}[.,]\d{2})""")
        for (m in re2.findAll(window)) {
            val q = m.groupValues[1].toDoubleOrNull() ?: continue
            val p = m.groupValues[2].brl() ?: continue
            if (q > 0 && p > 0 && p < 100_000) return Triple(q, p, q * p)
        }
        return null
    }

    private data class NumHit(
        val qty: Double,
        val price: Double,
        val total: Double,
        val dc: String,
        val around: String
    )

    private fun extractAllQtyPriceTotal(text: String): List<NumHit> {
        val re = Regex("""(\d{1,6})\s+(\d{1,6}[.,]\d{2})\s+(\d{1,8}[.,]\d{2})\s*([DCdc])?""")
        return re.findAll(text).mapNotNull { m ->
            val q = m.groupValues[1].toDoubleOrNull() ?: return@mapNotNull null
            val p = m.groupValues[2].brl() ?: return@mapNotNull null
            val tot = m.groupValues[3].brl() ?: (q * p)
            if (q <= 0 || p <= 0) return@mapNotNull null
            val dc = m.groupValues.getOrNull(4)?.ifBlank { "D" } ?: "D"
            val around = text.substring(
                (m.range.first - 80).coerceAtLeast(0),
                (m.range.last + 80).coerceAtMost(text.length)
            )
            NumHit(q, p, tot, dc, around)
        }.toList()
    }

    private fun detectSide(window: String): String {
        val u = window.uppercase(Locale.ROOT)
        // Coluna D/C: D=débito (compra), C=crédito (venda)
        Regex("""(\d[.,]\d{2})\s*([DC])""").find(u)?.groupValues?.getOrNull(2)?.let {
            return if (it == "C") "SELL" else "BUY"
        }
        if (Regex("""\bV\s*(FRA|VIS|FRAC|VISC)?\b""").containsMatchIn(u)) return "SELL"
        return "BUY"
    }

    private fun extractPregaoDate(text: String): Long? {
        val re = Regex(
            """(?:DATA\s*PREG[AÃ]O|PREG[AÃ]O)[^\d]{0,30}(\d{2})[/\-.](\d{2})[/\-.](\d{4})""",
            RegexOption.IGNORE_CASE
        )
        val m = re.find(text)
            ?: Regex("""\b(\d{2})/(\d{2})/(20\d{2})\b""").find(text)
            ?: return null
        val d = m.groupValues[1].toInt()
        val mo = m.groupValues[2].toInt()
        val y = m.groupValues[3].toInt()
        if (mo !in 1..12 || d !in 1..31) return null
        return Calendar.getInstance().apply {
            set(y, mo - 1, d, 12, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    private fun String.brl(): Double? =
        trim().replace(".", "").replace(',', '.').toDoubleOrNull()
}
