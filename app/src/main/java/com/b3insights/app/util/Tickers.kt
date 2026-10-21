package com.b3insights.app.util

import com.b3insights.app.data.model.AssetType

private val KNOWN_CRYPTO = setOf(
    "BTC", "ETH", "SOL", "BNB", "XRP", "ADA", "DOGE", "AVAX", "DOT", "LINK",
    "LTC", "ATOM", "MATIC", "POL", "SHIB", "TRX", "UNI", "NEAR", "APT", "ARB",
    "OP", "SUI", "PEPE", "TON", "XLM", "BCH", "ETC", "FIL", "ICP", "AAVE"
)

fun isCryptoTicker(raw: String): Boolean {
    val t = raw.trim().uppercase()
    if (t.contains("-USD") || t.contains("-BRL") || t.endsWith("USDT")) return true
    val base = t.substringBefore("-").substringBefore("/")
    return base in KNOWN_CRYPTO
}

/** Normaliza tickers da B3. Cripto: BTC → BTC-USD para cotação. */
fun normalizeB3Ticker(raw: String): String {
    val t = raw.trim().uppercase()
    if (t.contains("-USD") || t.contains("-BRL") || t.endsWith("USDT")) return t
    val base = t.removeSuffix(".SA").removeSuffix(".SAO")
    // Não converter cripto pura aqui — quem precisa de cotação usa quoteTickerFor()
    return base
}

/**
 * Símbolo usado só para buscar cotação.
 * BTC (carteira em R$) → BTC-USD no Yahoo; PETR4 → PETR4.SA
 */
fun quoteTickerFor(raw: String, assetType: String? = null): String {
    val t = normalizeB3Ticker(raw)
    val crypto = assetType == AssetType.CRIPTO.name || isCryptoTicker(t)
    if (crypto) {
        return when {
            t.endsWith("-USD") || t.endsWith("USDT") -> t
            t.endsWith("-BRL") -> t.removeSuffix("-BRL") + "-USD"
            else -> "$t-USD"
        }
    }
    return t
}

/** Símbolo no Yahoo Finance. */
fun isUsStockTicker(raw: String): Boolean {
    val t = raw.trim().uppercase().removeSuffix(".US")
    if (t.contains("-USD") || t.contains("-BRL") || t.endsWith(".SA")) return false
    if (t.matches(Regex("^[A-Z]{1,5}$"))) return true
    if (t.matches(Regex("^[A-Z]{1,4}-[A-Z]$"))) return true
    return false
}

fun toYahooSymbol(raw: String): String {
    val ticker = raw.trim().uppercase()
    if (ticker.contains("-USD") || ticker.contains("-BRL") || ticker.endsWith("USDT") ||
        ticker.contains("=") // FX USDBRL=X
    ) {
        return ticker
    }
    if (isCryptoTicker(ticker)) {
        val base = ticker.substringBefore("-").substringBefore(".")
        return "$base-USD"
    }
    // Ações americanas: Yahoo usa o símbolo sem .SA
    if (isUsStockTicker(ticker)) {
        return ticker.removeSuffix(".US")
    }
    val b3 = ticker.removeSuffix(".SA").removeSuffix(".SAO")
    return if (b3.endsWith(".SA")) b3 else "$b3.SA"
}

/** Unidades (B3) terminadas em 11 que NÃO são FII. */
private val KNOWN_UNITS = setOf(
    "KLBN11", "TAEE11", "SANB11", "ITSA11", "ALUP11", "ENGI11", "CPLE11",
    "TIET11", "SAPR11", "AESB11", "BRAP11", "PATI11"
)

/** ETFs B3 comuns terminados em 11. */
private val KNOWN_ETFS = setOf(
    "BOVA11", "SMAL11", "IVVB11", "HASH11", "GOLD11", "NASD11", "PIBB11",
    "XFIX11", "BOVV11", "BRAX11", "FIND11", "GOVE11", "ISUS11", "MATB11",
    "DIVO11", "SPXI11", "WRLD11", "ACWI11", "EURP11", "ESGB11"
)

fun detectAssetType(apiType: String?, ticker: String, name: String?): AssetType {
    val t = ticker.uppercase().removeSuffix(".SA").removeSuffix(".SAO")
    if (isUsStockTicker(t)) return AssetType.ACAO
    if (isCryptoTicker(t) ||
        apiType?.uppercase() in setOf("CRYPTOCURRENCY", "CRYPTO")
    ) {
        return AssetType.CRIPTO
    }
    val n = (name ?: "").uppercase()
    // Nome e listas conhecidas têm prioridade sobre o tipo genérico da API (EQUITY)
    if (n.contains("FII") || n.contains("FUNDO DE INVESTIMENTO IMOBILI")) return AssetType.FII
    if (n.contains("ETF") || n.contains("INDEX FUND") || n.contains("ÍNDICE") || n.contains("INDICE")) return AssetType.ETF
    if (t in KNOWN_ETFS) return AssetType.ETF
    if (t in KNOWN_UNITS) return AssetType.ACAO
    val fromApi = AssetType.fromApiType(apiType)
    if (fromApi == AssetType.ETF) return AssetType.ETF
    if (fromApi == AssetType.FII) return AssetType.FII
    if (fromApi == AssetType.ACAO) {
        // Yahoo marca FII/unit/ETF como EQUITY — não confiar só nisso se termina em 11
        if (t.endsWith("11") && t !in KNOWN_UNITS && t !in KNOWN_ETFS) {
            // heurística: 4 letras + 11 costuma ser FII; unidades já listadas acima
            if (t.length >= 5 && t[0].isLetter()) return AssetType.FII
        }
        return AssetType.ACAO
    }
    return when {
        t.endsWith("11") && t in KNOWN_ETFS -> AssetType.ETF
        t.endsWith("11") && t in KNOWN_UNITS -> AssetType.ACAO
        t.endsWith("11") && !t.endsWith("B11") -> AssetType.FII
        else -> AssetType.ACAO
    }
}
