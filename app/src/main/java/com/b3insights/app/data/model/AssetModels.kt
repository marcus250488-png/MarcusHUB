package com.b3insights.app.data.model

/** Tipos de ativos negociados na B3 suportados pelo app. */
enum class AssetType(val label: String) {
    ACAO("Ação"),
    FII("Fundo Imobiliário"),
    ETF("ETF"),
    CRIPTO("Criptomoeda"),
    OUTRO("Outro");

    companion object {
        fun fromApiType(raw: String?): AssetType = when (raw?.uppercase()) {
            "STOCK", "EQUITY", "ACAO" -> ACAO
            "FUND", "FII", "MUTUALFUND" -> FII
            "ETF" -> ETF
            "CRYPTOCURRENCY", "CRYPTO", "CRIPTO" -> CRIPTO
            else -> OUTRO
        }
    }
}

/**
 * Envelope de resultado usado em todo o app para diferenciar sucesso, erro e "sem dado",
 * em vez de inventar valores quando a fonte de dados não retorna a informação.
 */
sealed class DataResult<out T> {
    data class Success<T>(val data: T, val fromCache: Boolean = false) : DataResult<T>()
    data class Error(val message: String) : DataResult<Nothing>()
    object Loading : DataResult<Nothing>()
}

/** Cotação/resumo de um ativo, usado na busca, favoritos e cabeçalho da página do ativo. */
data class AssetQuote(
    val ticker: String,
    val name: String?,
    val assetType: AssetType,
    val sector: String?,
    val currentPrice: Double?,
    val dayChange: Double?,
    val dayChangePercent: Double?,
    val dayHigh: Double?,
    val dayLow: Double?,
    val volume: Long?,
    val marketCap: Double?,
    val lastUpdated: Long?,
    val logoUrl: String? = null
)

/** Ponto de uma série histórica de preço, para o gráfico. */
data class PricePoint(val timestamp: Long, val close: Double)

/** Indicadores fundamentalistas — nulos quando a fonte não fornece o dado. */
data class AssetIndicators(
    val pl: Double? = null,
    val pvp: Double? = null,
    val roe: Double? = null,
    val roic: Double? = null,
    val dividendYield: Double? = null,
    val netMargin: Double? = null,
    val ebitdaMargin: Double? = null,
    val netDebtToEbitda: Double? = null,
    val lpa: Double? = null,
    val vpa: Double? = null,
    val revenue: Double? = null,
    val netIncome: Double? = null,
    val revenueGrowth: Double? = null,
    val profitGrowth: Double? = null,
    val marketCap: Double? = null,
    val freeFloat: Double? = null,
    // Específicos de FIIs
    val dividendPerShare: Double? = null,
    val vacancy: Double? = null,
    val netWorth: Double? = null,
    val numberOfShareholders: Int? = null,
    val numberOfProperties: Int? = null,
    val capRate: Double? = null,
    val monthlyYield: Double? = null,
    val annualYield: Double? = null,
    val quotaPrice: Double? = null,
    val quotaBookValue: Double? = null
)

data class DividendEntry(
    val exDate: Long,
    val paymentDate: Long?,
    val valuePerShare: Double,
    val type: String // "Dividendo", "JCP", "Rendimento"
)

data class NewsItem(
    val title: String,
    val source: String,
    val date: Long,
    val summary: String?,
    val imageUrl: String?,
    val url: String
)

data class ReportItem(
    val title: String,
    val date: Long,
    val type: String,
    val url: String,
    val description: String?
)

data class BalanceSheetEntry(
    val periodLabel: String,
    val totalAssets: Double?,
    val totalLiabilities: Double?,
    val netWorth: Double?,
    val cash: Double?,
    val totalDebt: Double?,
    val netDebt: Double?,
    val receivables: Double?,
    val fixedAssets: Double?
)

data class ResultsEntry(
    val periodLabel: String,
    val revenue: Double?,
    val grossProfit: Double?,
    val ebitda: Double?,
    val ebit: Double?,
    val netIncome: Double?,
    val netMargin: Double?,
    val ebitdaMargin: Double?
)

/** Página completa de um ativo — agrega tudo que as abas de detalhe precisam exibir. */
data class AssetDetail(
    val quote: AssetQuote,
    val about: String?,
    val indicators: AssetIndicators,
    val dividends: List<DividendEntry>,
    val balanceSheet: List<BalanceSheetEntry>,
    val results: List<ResultsEntry>,
    val news: List<NewsItem>,
    val reports: List<ReportItem>,
    val priceHistory: List<PricePoint>
)

enum class ChartPeriod(val label: String, val range: String, val interval: String) {
    D1("1D", "1d", "5m"),
    W1("1S", "5d", "30m"),
    M1("1M", "1mo", "1d"),
    M3("3M", "3mo", "1d"),
    M6("6M", "6mo", "1d"),
    Y1("1A", "1y", "1d"),
    Y5("5A", "5y", "1wk"),
    MAX("Máx.", "max", "1mo")
}
