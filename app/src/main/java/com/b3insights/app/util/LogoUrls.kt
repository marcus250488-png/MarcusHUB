package com.b3insights.app.util

import java.util.concurrent.ConcurrentHashMap

/**
 * Fontes públicas de logos, escolhidas por tipo de ativo (sem token, sem chave no código):
 *  - Ações / FIIs / BDRs / ETFs da B3 → ícones da brapi.dev (SVG), pelo ticker
 *  - Stocks dos EUA (XEL, MO, TMO…)   → imagens do Financial Modeling Prep, pelo ticker
 *  - Criptomoedas                     → set "cryptocurrency-icons" (domínio público) via jsDelivr
 *  - FIIs mapeados em FII_MANAGER_DOMAIN → favicon do site da gestora
 *
 * Nenhuma fonte cobre 100% dos ativos: quando todas falham, o AssetLogo mostra as iniciais.
 * Confira os termos de uso de cada fonte antes de publicar o app na Play Store.
 */
private const val BRAPI_ICONS = "https://icons.brapi.dev/icons/"
private const val FMP_IMAGES = "https://financialmodelingprep.com/image-stock/"
private const val CRYPTO_ICONS =
    "https://cdn.jsdelivr.net/gh/spothq/cryptocurrency-icons@master/128/color/"
private const val FAVICON = "https://www.google.com/s2/favicons?sz=128&domain="

/**
 * FIIs → site da gestora (o ícone vem do favicon do site).
 * Motivo: a brapi devolve um quadrado genérico "H" para vários fundos de gestoras diferentes
 * (Kinea, XP, Pátria…) e nada para outros. Para os tickers abaixo o app usa só o ícone da gestora.
 * Fundos que a brapi já traz com logo próprio (BROF11, TRXF11, VGIR11, VILG11) ficam de fora.
 * Para incluir um fundo novo: "TICKER11" to "site-da-gestora.com.br".
 */
private val FII_MANAGER_DOMAIN: Map<String, String> = mapOf(
    // Kinea
    "KFOF11" to "kinea.com.br", "KNCR11" to "kinea.com.br", "KNIP11" to "kinea.com.br",
    "KNRI11" to "kinea.com.br", "KNSC11" to "kinea.com.br",
    // XP Asset
    "MXRF11" to "xpasset.com.br", "XPLG11" to "xpasset.com.br",
    "XPML11" to "xpasset.com.br", "XPSF11" to "xpasset.com.br",
    // Pátria (ex-CSHG)
    "HGLG11" to "patria.com", "HGRE11" to "patria.com", "HGCR11" to "patria.com",
    "HGFF11" to "patria.com", "HGRU11" to "patria.com", "PATL11" to "patria.com",
    // RBR
    "RBRF11" to "rbrasset.com.br", "RBRP11" to "rbrasset.com.br", "RBRR11" to "rbrasset.com.br",
    // Rio Bravo
    "RBVA11" to "riobravo.com.br", "RCRB11" to "riobravo.com.br",
    // REC
    "RECR11" to "recgestao.com.br", "RECT11" to "recgestao.com.br",
    // Vinci
    "VISC11" to "vincipartners.com",
    // BTG Pactual
    "BTLG11" to "btgpactual.com",
    // Demais gestoras
    "LVBI11" to "vbirealestate.com",
    "MCCI11" to "mauacapital.com",
    "OUFF11" to "ourinvest.com.br",
    "HSML11" to "hsi.com.br",
    "HGBS11" to "hedgeinvest.com.br",
    "CPTS11" to "capitaniainvestimentos.com.br",
    "TGAR11" to "tgcore.com.br",
    "VRTA11" to "fator.com.br",
    "ALZR11" to "alianza.com.br"
)

/**
 * Lista ordenada de URLs candidatas para o logo do ativo.
 * [apiLogo] (ex.: campo logourl da brapi) tem prioridade quando vier em https.
 */
fun logoCandidates(
    ticker: String,
    assetType: String? = null,
    apiLogo: String? = null
): List<String> {
    val raw = ticker.trim().uppercase()
    if (raw.isEmpty()) return emptyList()

    val urls = LinkedHashSet<String>()
    apiLogo?.trim()
        ?.takeIf { it.startsWith("https://") }
        ?.let { urls.add(it) }

    val isCrypto = assetType == "CRIPTO" || isCryptoTicker(raw)
    when {
        isCrypto -> {
            val base = raw
                .substringBefore("-")
                .substringBefore("/")
                .removeSuffix("USDT")
                .lowercase()
            if (base.isNotEmpty()) urls.add("$CRYPTO_ICONS$base.png")
        }
        isUsStockTicker(raw) -> {
            val us = raw.removeSuffix(".US")
            urls.add("$FMP_IMAGES$us.png")
        }
        else -> {
            val b3 = raw.removeSuffix(".SA").removeSuffix(".SAO")
            val managerDomain = FII_MANAGER_DOMAIN[b3]
            if (managerDomain != null) {
                // Ignora até o apiLogo: a brapi só tem o quadrado genérico para esses fundos.
                return listOf("$FAVICON$managerDomain")
            }
            if (b3.isNotEmpty()) urls.add("$BRAPI_ICONS$b3.svg")
        }
    }
    return urls.toList()
}

/**
 * Memória (só da sessão) das URLs que já se mostraram inexistentes/inválidas,
 * para não repetir requisição a cada rolagem de lista.
 * Falhas de rede (offline/timeout) NÃO entram aqui, para tentar de novo depois.
 */
object LogoMisses {
    private val missing = ConcurrentHashMap.newKeySet<String>()
    fun contains(url: String): Boolean = missing.contains(url)
    fun add(url: String) {
        missing.add(url)
    }
}
