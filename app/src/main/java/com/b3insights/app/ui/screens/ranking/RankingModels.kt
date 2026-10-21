package com.b3insights.app.ui.screens.ranking

import com.b3insights.app.data.model.FiiCatalog

enum class RankingCategory(val title: String, val key: String) {
    ACOES("Ações", "ACOES"),
    FIIS("FIIs", "FIIS"),
    ETFS("ETFs", "ETFS"),
    CRIPTO("Criptomoedas", "CRIPTO");

    /** Liberado: sem bloqueio PRO. */
    val metricsRequirePro: Boolean
        get() = false
}

enum class RankingMetric(
    val title: String,
    val description: String,
    val categories: Set<RankingCategory>,
    /** Se true, bloqueia FREE com cadeado + modal (quando a categoria também for PRO). */
    val isProFeature: Boolean = false
) {
    MAIOR_CAPITALIZACAO(
        "Maior Capitalização",
        "Empresas ou ativos com maior valor de mercado.",
        setOf(RankingCategory.ACOES, RankingCategory.FIIS, RankingCategory.ETFS, RankingCategory.CRIPTO)
    ),
    MAIORES_ALTAS_DIA(
        "Maiores Altas do Dia",
        "Maiores altas percentuais na cotação de hoje.",
        setOf(RankingCategory.ACOES, RankingCategory.FIIS, RankingCategory.ETFS, RankingCategory.CRIPTO)
    ),
    MAIORES_BAIXAS_DIA(
        "Maiores Baixas do Dia",
        "Maiores quedas percentuais na cotação de hoje.",
        setOf(RankingCategory.ACOES, RankingCategory.FIIS, RankingCategory.ETFS, RankingCategory.CRIPTO)
    ),
    DIVIDEND_YIELD(
        "Dividend Yield",
        "Quanto o ativo paga de proventos em relação ao preço.",
        setOf(RankingCategory.ACOES, RankingCategory.FIIS)
    ),
    MENOR_PL(
        "Menores P/L",
        "Preço dividido pelo lucro por ação — múltiplo mais baixo.",
        setOf(RankingCategory.ACOES)
    ),
    MENOR_PVP(
        "Menores P/VP",
        "Preço em relação ao valor patrimonial — múltiplo mais baixo.",
        setOf(RankingCategory.ACOES, RankingCategory.FIIS)
    ),
    MAIOR_ROE(
        "Maiores ROEs",
        "Retorno sobre o patrimônio líquido — eficiência do capital.",
        setOf(RankingCategory.ACOES)
    ),
    MAIOR_MARGEM(
        "Margem Líquida",
        "Lucro líquido em percentual da receita da empresa.",
        setOf(RankingCategory.ACOES)
    ),
    MAIORES_RECEITAS(
        "Maiores Receitas",
        "Empresas com maior faturamento no período.",
        setOf(RankingCategory.ACOES)
    ),
    MAIORES_LUCROS(
        "Maiores Lucros",
        "Empresas com maior lucro líquido no período.",
        setOf(RankingCategory.ACOES)
    ),
    MAIS_BARATAS_GRAHAM(
        "Mais Baratas (Graham)",
        "Maior margem entre cotação e o valor estimado por Graham.",
        setOf(RankingCategory.ACOES)
    ),
    PRECO_TETO_BAZIN(
        "Preço-teto Bazin",
        "Maior margem abaixo do teto pelo yield mínimo de Bazin.",
        setOf(RankingCategory.ACOES, RankingCategory.FIIS)
    ),
    VOLUME(
        "Maior Volume",
        "Mais negociados no dia (maior liquidez).",
        setOf(RankingCategory.ACOES, RankingCategory.FIIS, RankingCategory.ETFS, RankingCategory.CRIPTO)
    ),
    AS_MAIS_QUERIDAS(
        "As mais queridas",
        "Ativos em destaque por interesse e volume de negociação.",
        setOf(RankingCategory.ACOES, RankingCategory.FIIS, RankingCategory.CRIPTO)
    );

    fun requiresProFor(category: RankingCategory): Boolean = false
}

object RankingUniverse {
    val acoes = listOf(
        "PETR4", "VALE3", "ITUB4", "BBDC4", "BBAS3", "WEGE3", "ABEV3", "B3SA3",
        "RENT3", "SUZB3", "PRIO3", "EQTL3", "VIVT3", "JBSS3", "GGBR4", "CSAN3",
        "LREN3", "MGLU3", "HAPV3", "RAIL3", "BPAC11", "ITSA4", "EMBR3", "RADL3",
        "TOTS3", "CMIG4", "ENEV3", "SBSP3", "CCRO3", "UGPA3", "KLBN11", "TAEE11"
    )
    val fiis = FiiCatalog.all.map { it.ticker }.take(80)
    val etfs = listOf("BOVA11", "SMAL11", "IVVB11", "HASH11", "GOLD11", "NASD11", "PIBB11", "XFIX11", "DIVO11", "FIND11", "MATB11", "ISUS11", "ECOO11", "XINA11")
    val cripto = listOf("BTC-USD", "ETH-USD", "SOL-USD", "BNB-USD", "XRP-USD", "ADA-USD", "DOGE-USD", "AVAX-USD")

    fun forCategory(cat: RankingCategory): List<String> = tickers(cat)

    fun tickers(cat: RankingCategory): List<String> = when (cat) {
        RankingCategory.ACOES -> acoes
        RankingCategory.FIIS -> fiis
        RankingCategory.ETFS -> etfs
        RankingCategory.CRIPTO -> cripto
    }
}

data class RankingRow(
    val ticker: String,
    val name: String?,
    val price: Double?,
    val changePercent: Double?,
    val marketCap: Double?,
    val volume: Long?,
    val pl: Double? = null,
    val pvp: Double? = null,
    val dy: Double? = null,
    val roe: Double? = null,
    val netMargin: Double? = null,
    val revenue: Double? = null,
    val netIncome: Double? = null,
    val lpa: Double? = null,
    val vpa: Double? = null,
    val grahamPrice: Double? = null,
    val bazinPrice: Double? = null,
    val marginGraham: Double? = null,
    val marginBazin: Double? = null,
    /** DPS anual (R$) usado no Bazin */
    val bazinDps: Double? = null,
    /** Origem do DPS (ex.: LTM via yield) */
    val bazinPeriod: String? = null,
    val metricValue: Double? = null,
    val metricLabel: String? = null,
    val logoUrl: String? = null
)
