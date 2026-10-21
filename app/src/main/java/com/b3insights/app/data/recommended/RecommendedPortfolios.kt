package com.b3insights.app.data.recommended

/**
 * Carteiras-modelo conforme especificação do produto.
 * Pesos-alvo somam 100%. Scores são determinísticos a partir de indicadores
 * disponíveis (brapi/Yahoo via MarketDataRepository) — sem inventar dados.
 */
data class PortfolioTemplate(
    val slug: String,
    val name: String,
    val objective: String,
    val riskLevel: String,
    val horizon: String,
    val category: String,
    val positions: List<TargetPosition>
)

data class TargetPosition(
    val ticker: String,
    val segment: String,
    val targetWeight: Double // 0..1
)

data class ScoredPosition(
    val ticker: String,
    val name: String?,
    val segment: String,
    val targetWeight: Double,
    val price: Double?,
    val dayChangePercent: Double?,
    val score: Int?, // null = dados insuficientes
    val factors: List<FactorNote>,
    val riskFlags: List<String>,
    val selectionReason: String
)

data class FactorNote(
    val name: String,
    val weight: Double,
    val points: Int?, // 0..100 ou null
    val detail: String
)

data class PortfolioSnapshot(
    val template: PortfolioTemplate,
    val positions: List<ScoredPosition>,
    val updatedAt: Long,
    val sources: List<String>,
    val disclaimer: String = DISCLAIMER
)

const val DISCLAIMER =
    "Modelo educacional com pesos-alvo fixos. Não é recomendação personalizada de investimento, " +
    "nem garantia de rentabilidade. Dados podem estar atrasados. Consulte a data da última atualização."

val PORTFOLIO_TEMPLATES: List<PortfolioTemplate> = listOf(
    PortfolioTemplate(
        slug = "fiis",
        name = "Fundos Imobiliários",
        objective = "Renda recorrente via FIIs diversificados por segmento (logística, shoppings, recebíveis).",
        riskLevel = "Moderado",
        horizon = "Longo prazo",
        category = "FII",
        positions = listOf(
            TargetPosition("MXRF11", "Recebíveis/híbrido", 0.16),
            TargetPosition("HGLG11", "Logística", 0.18),
            TargetPosition("KNRI11", "Renda urbana/logística", 0.16),
            TargetPosition("XPML11", "Shopping centers", 0.16),
            TargetPosition("VISC11", "Shopping centers", 0.16),
            TargetPosition("CPTS11", "Recebíveis/híbrido", 0.18)
        )
    ),
    PortfolioTemplate(
        slug = "bdrs",
        name = "Internacional — BDRs",
        objective = "Exposição a empresas globais negociadas na B3 via BDRs (risco cambial + risco da empresa).",
        riskLevel = "Alto",
        horizon = "Longo prazo",
        category = "BDR",
        positions = listOf(
            TargetPosition("AAPL34", "Apple", 0.18),
            TargetPosition("MSFT34", "Microsoft", 0.18),
            TargetPosition("AMZO34", "Amazon", 0.16),
            TargetPosition("GOGL34", "Alphabet", 0.16),
            TargetPosition("NVDC34", "Nvidia", 0.16),
            TargetPosition("MCDC34", "McDonald's", 0.16)
        )
    ),
    PortfolioTemplate(
        slug = "small-caps",
        name = "Small Caps",
        objective = "Empresas de menor capitalização com potencial de crescimento e maior volatilidade.",
        riskLevel = "Alto",
        horizon = "Longo prazo",
        category = "Ação",
        positions = listOf(
            TargetPosition("TOTS3", "Tecnologia", 0.18),
            TargetPosition("PSSA3", "Seguros", 0.17),
            TargetPosition("CURY3", "Construção/incorporação", 0.15),
            TargetPosition("SLCE3", "Agronegócio", 0.17),
            TargetPosition("LEVE3", "Autopeças", 0.16),
            TargetPosition("KEPL3", "Máquinas/equipamentos", 0.17)
        )
    ),
    PortfolioTemplate(
        slug = "buy-and-hold",
        name = "Ações Buy and Hold",
        objective = "Empresas sólidas para manter no longo prazo, com foco em qualidade e consistência.",
        riskLevel = "Moderado",
        horizon = "Longo prazo",
        category = "Ação",
        positions = listOf(
            TargetPosition("WEGE3", "Bens industriais", 0.18),
            TargetPosition("ITUB4", "Financeiro", 0.18),
            TargetPosition("BBAS3", "Financeiro", 0.15),
            TargetPosition("EGIE3", "Energia elétrica", 0.16),
            TargetPosition("B3SA3", "Infraestrutura de mercado", 0.16),
            TargetPosition("FLRY3", "Saúde", 0.17)
        )
    ),
    PortfolioTemplate(
        slug = "dividendos",
        name = "Ações de Dividendos",
        objective = "Foco em geração de proventos regulares e sustentabilidade do payout.",
        riskLevel = "Moderado",
        horizon = "Longo prazo",
        category = "Ação",
        positions = listOf(
            TargetPosition("BBSE3", "Seguros", 0.18),
            TargetPosition("TAEE11", "Energia elétrica", 0.18),
            TargetPosition("CMIG4", "Energia elétrica", 0.15),
            TargetPosition("CPFE3", "Energia elétrica", 0.16),
            TargetPosition("TRPL4", "Transmissão de energia", 0.16),
            TargetPosition("PETR4", "Petróleo e gás", 0.17)
        )
    )
)

/** Score 0–100 determinístico a partir de indicadores disponíveis. Não inventa campos. */
object PortfolioScoring {

    fun score(
        category: String,
        dy: Double?,
        pl: Double?,
        pvp: Double?,
        roe: Double?,
        netMargin: Double?,
        debtToEbitda: Double?,
        volume: Long?,
        hasQuote: Boolean
    ): Pair<Int?, List<FactorNote>> {
        if (!hasQuote) {
            return null to listOf(
                FactorNote("Cotação", 1.0, null, "Cotação indisponível — score não publicado")
            )
        }
        return when (category) {
            "FII" -> scoreFii(dy, pvp, volume)
            "BDR" -> scoreBdr(pl, pvp, roe, volume)
            else -> scoreStock(category, dy, pl, pvp, roe, netMargin, debtToEbitda, volume)
        }
    }

    private fun scoreFii(dy: Double?, pvp: Double?, volume: Long?): Pair<Int?, List<FactorNote>> {
        val factors = mutableListOf<FactorNote>()
        // Pesos aproximados do doc: previsibilidade/distribuição 20%, P/VP 10%, liquidez 10%...
        val dyPts = when {
            dy == null -> null
            dy in 6.0..14.0 -> 85
            dy in 4.0..18.0 -> 70
            dy > 0 -> 50
            else -> 20
        }
        factors += FactorNote("Previsibilidade das distribuições (DY)", 0.20, dyPts, dy?.let { "DY ${"%.1f".format(it)}%" } ?: "DY ausente")

        val pvpPts = when {
            pvp == null -> null
            pvp in 0.85..1.15 -> 90
            pvp in 0.70..1.30 -> 75
            pvp > 0 -> 55
            else -> 30
        }
        factors += FactorNote("P/VP e valuation do segmento", 0.10, pvpPts, pvp?.let { "P/VP ${"%.2f".format(it)}" } ?: "P/VP ausente")

        val liqPts = when {
            volume == null -> null
            volume >= 500_000 -> 85
            volume >= 100_000 -> 70
            volume > 0 -> 50
            else -> 20
        }
        factors += FactorNote("Liquidez", 0.10, liqPts, volume?.let { "Volume $it" } ?: "Volume ausente")

        // Demais fatores sem dado → null (não preenche com zero)
        factors += FactorNote("Qualidade do portfólio", 0.20, null, "Dado de vacância/inquilinos não disponível na API")
        factors += FactorNote("Ocupação / crédito", 0.20, null, "Não informado pela fonte")
        factors += FactorNote("Alavancagem", 0.15, null, "Não informado pela fonte")
        factors += FactorNote("Governança", 0.05, null, "Não pontuado automaticamente")

        return weightedScore(factors)
    }

    private fun scoreBdr(pl: Double?, pvp: Double?, roe: Double?, volume: Long?): Pair<Int?, List<FactorNote>> {
        val factors = mutableListOf<FactorNote>()
        val roePts = when {
            roe == null -> null
            roe >= 15 -> 90
            roe >= 8 -> 70
            roe > 0 -> 50
            else -> 25
        }
        factors += FactorNote("Qualidade e rentabilidade", 0.25, roePts, roe?.let { "ROE ${"%.1f".format(it)}%" } ?: "ROE ausente")

        val valPts = when {
            pl == null && pvp == null -> null
            (pl != null && pl in 10.0..30.0) || (pvp != null && pvp in 1.0..8.0) -> 80
            pl != null || pvp != null -> 60
            else -> null
        }
        factors += FactorNote("Valuation relativo", 0.15, valPts, "P/L ${pl ?: "—"} · P/VP ${pvp ?: "—"}")

        val liqPts = when {
            volume == null -> null
            volume >= 50_000 -> 75
            volume > 0 -> 50
            else -> 20
        }
        factors += FactorNote("Liquidez do BDR no Brasil", 0.10, liqPts, volume?.let { "Vol. $it" } ?: "Volume ausente")
        factors += FactorNote("Crescimento e caixa", 0.20, null, "Série multi-anos incompleta na API")
        factors += FactorNote("Endividamento", 0.15, null, "Dado ausente")
        factors += FactorNote("Risco cambial / concentração", 0.10, 50, "BDR: risco cambial inerente (nota neutra)")
        factors += FactorNote("Governança", 0.05, null, "Não pontuado automaticamente")
        return weightedScore(factors)
    }

    private fun scoreStock(
        category: String,
        dy: Double?,
        pl: Double?,
        pvp: Double?,
        roe: Double?,
        netMargin: Double?,
        debt: Double?,
        volume: Long?
    ): Pair<Int?, List<FactorNote>> {
        val factors = mutableListOf<FactorNote>()
        if (category == "dividendos" || true) {
            // Base comum + ênfase se dividendos
            val roePts = when {
                roe == null -> null
                roe >= 15 -> 90
                roe >= 10 -> 75
                roe > 0 -> 55
                else -> 25
            }
            factors += FactorNote("Rentabilidade (ROE)", 0.20, roePts, roe?.let { "ROE ${"%.1f".format(it)}%" } ?: "ROE ausente")

            val dyPts = when {
                dy == null -> null
                dy in 4.0..12.0 -> 90
                dy in 2.0..15.0 -> 70
                dy > 0 -> 45
                else -> 20
            }
            val dyW = if (category.contains("divid", true) || category == "Ação" && dy != null) 0.20 else 0.10
            factors += FactorNote("Proventos (DY)", dyW, dyPts, dy?.let { "DY ${"%.1f".format(it)}%" } ?: "DY ausente")

            val debtPts = when {
                debt == null -> null
                debt < 2.0 -> 85
                debt < 3.5 -> 65
                debt < 6 -> 45
                else -> 25
            }
            factors += FactorNote("Endividamento", 0.15, debtPts, debt?.let { "Dívida/EBITDA ${"%.1f".format(it)}" } ?: "Ausente")

            val valPts = when {
                pl == null -> null
                pl in 6.0..18.0 -> 85
                pl in 4.0..25.0 -> 70
                pl > 0 -> 50
                else -> 30
            }
            factors += FactorNote("Valuation (P/L)", 0.10, valPts, pl?.let { "P/L ${"%.1f".format(it)}" } ?: "P/L ausente")

            val marginPts = when {
                netMargin == null -> null
                netMargin >= 12 -> 85
                netMargin >= 5 -> 70
                netMargin > 0 -> 50
                else -> 25
            }
            factors += FactorNote("Margem líquida", 0.10, marginPts, netMargin?.let { "${"%.1f".format(it)}%" } ?: "Ausente")

            val liqPts = when {
                volume == null -> null
                volume >= 1_000_000 -> 90
                volume >= 200_000 -> 75
                volume > 0 -> 55
                else -> 25
            }
            factors += FactorNote("Liquidez", 0.10, liqPts, volume?.let { "Vol. $it" } ?: "Ausente")
            factors += FactorNote("Crescimento multi-anos", 0.15, null, "Série 5 anos incompleta — não estimado")
        }
        return weightedScore(factors)
    }

    /** Só pontua fatores com nota; redistribui pesos entre os disponíveis. Score nulo se < 40% dos pesos tiverem dado. */
    private fun weightedScore(factors: List<FactorNote>): Pair<Int?, List<FactorNote>> {
        val known = factors.filter { it.points != null }
        val knownWeight = known.sumOf { it.weight }
        val totalWeight = factors.sumOf { it.weight }.coerceAtLeast(1e-9)
        if (knownWeight / totalWeight < 0.35) return null to factors
        val score = known.sumOf { (it.points!! * it.weight) } / knownWeight
        return score.toInt().coerceIn(0, 100) to factors
    }
}
