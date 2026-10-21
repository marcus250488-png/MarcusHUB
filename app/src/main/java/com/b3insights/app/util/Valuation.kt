package com.b3insights.app.util

import kotlin.math.sqrt

/**
 * Graham: √(22,5 × LPA × VPA)
 * Bazin: DPS_anual ÷ 0,06  (yield-alvo de 6%)
 *
 * DPS anual = proventos dos **últimos 12 meses (LTM)**.
 * Preferência:
 *  1) cotação × (Dividend Yield % / 100) quando o DY for confiável
 *  2) soma explícita dos proventos 12m, se for coerente com o DY
 *
 * Nunca usar um provento isolado (último pagamento) como se fosse o total anual —
 * isso gerava tetos absurdos (ex.: R$ 0,30 para ações de R$ 40).
 */
object Valuation {

    data class GrahamBreakdown(
        val fairPrice: Double,
        val lpa: Double,
        val vpa: Double,
        val lpaSource: String,
        val vpaSource: String,
        val formula: String = "√(22,5 × LPA × VPA)",
        val note: String = "22,5 = P/L 15 × P/VP 1,5 (régua clássica de Benjamin Graham)."
    )

    data class BazinBreakdown(
        val ceilingPrice: Double,
        val dividendsPerShare: Double,
        val dividendPeriod: String,
        val yieldTargetPercent: Double = 6.0,
        val formula: String = "DPS anual ÷ 0,06",
        val note: String = "Yield-alvo de 6% a.a. (método Bazin). DPS = proventos dos últimos 12 meses."
    )

    fun grahamPrice(lpa: Double?, vpa: Double?, price: Double?, pl: Double?, pvp: Double?): Double? =
        grahamBreakdown(lpa, vpa, price, pl, pvp)?.fairPrice

    fun grahamBreakdown(
        lpa: Double?,
        vpa: Double?,
        price: Double?,
        pl: Double?,
        pvp: Double?
    ): GrahamBreakdown? {
        val eps: Pair<Double, String>? = when {
            lpa != null && lpa > 0 -> lpa to "LPA informado pela fonte de dados"
            price != null && pl != null && pl > 0 -> (price / pl) to "LPA estimado = cotação ÷ P/L"
            else -> null
        }
        val bv: Pair<Double, String>? = when {
            vpa != null && vpa > 0 -> vpa to "VPA informado pela fonte de dados"
            price != null && pvp != null && pvp > 0 -> (price / pvp) to "VPA estimado = cotação ÷ P/VP"
            else -> null
        }
        if (eps == null || bv == null) return null
        val g = sqrt(22.5 * eps.first * bv.first)
        if (g.isNaN() || g.isInfinite() || g <= 0) return null
        return GrahamBreakdown(
            fairPrice = g,
            lpa = eps.first,
            vpa = bv.first,
            lpaSource = eps.second,
            vpaSource = bv.second
        )
    }

    fun bazinCeiling(
        price: Double?,
        dividendYieldPercent: Double?,
        annualDividendsPerShare: Double? = null,
        lastDividendPerShare: Double? = null
    ): Double? = bazinBreakdown(price, dividendYieldPercent, annualDividendsPerShare)?.ceilingPrice

    fun bazinBreakdown(
        price: Double?,
        dividendYieldPercent: Double?,
        annualDividendsPerShare: Double? = null
    ): BazinBreakdown? {
        val dyPct = normalizeYieldPercent(dividendYieldPercent)

        // DPS implícito pelo yield: cotação × DY
        val dpsFromYield = if (price != null && price > 0 && dyPct != null && dyPct > 0) {
            price * (dyPct / 100.0)
        } else null

        // Soma explícita (deve ser LTM; rejeita se parecer provento isolado)
        val dpsExplicit = annualDividendsPerShare?.takeIf { it > 0 }

        val (dps, period) = selectDps(price, dpsFromYield, dpsExplicit, dyPct) ?: return null

        val ceiling = dps / 0.06
        if (ceiling.isNaN() || ceiling.isInfinite() || ceiling <= 0) return null
        return BazinBreakdown(
            ceilingPrice = ceiling,
            dividendsPerShare = dps,
            dividendPeriod = period
        )
    }

    /**
     * Escolhe o DPS anual mais confiável.
     * Se a "soma explícita" for muito menor que cotação×DY (ex.: < 40%),
     * trata como provento isolado / dado sujo e usa o yield.
     */
    private fun selectDps(
        price: Double?,
        dpsFromYield: Double?,
        dpsExplicit: Double?,
        dyPct: Double?
    ): Pair<Double, String>? {
        val yieldOk = dpsFromYield != null && dpsFromYield > 0
        val explicitOk = dpsExplicit != null && dpsExplicit > 0

        if (yieldOk && explicitOk) {
            // Explicit muito baixo vs yield → provavelmente último dividendo, não LTM
            if (dpsExplicit!! < dpsFromYield!! * 0.40) {
                return dpsFromYield to "LTM via cotação × DY ${"%.1f".format(dyPct)}% (API enviou provento parcial; ignorado)"
            }
            // Explicit muito alto vs yield (> 2,5×) → suspeito; prefere yield
            if (dpsExplicit > dpsFromYield * 2.5) {
                return dpsFromYield to "LTM via cotação × DY ${"%.1f".format(dyPct)}% (soma API inconsistente; ignorada)"
            }
            // Ambos coerentes: usa a média (mais estável) ou o maior conservador para teto
            // Para Bazin o teto sobe com DPS maior — usamos a média para equilíbrio
            val mid = (dpsExplicit + dpsFromYield) / 2.0
            return mid to "LTM médio (soma proventos + cotação × DY ${"%.1f".format(dyPct)}%)"
        }

        if (yieldOk) {
            // Sanity: Bazin = dps/0.06 não deve ser dezenas de vezes a cotação se DY for baixo
            val ceiling = dpsFromYield!! / 0.06
            if (price != null && price > 0 && ceiling > price * 12 && (dyPct ?: 0.0) < 3.0) {
                // DY suspeito — tenta explicit se houver
                if (explicitOk) {
                    val iy = (dpsExplicit!! / price) * 100.0
                    if (iy >= 0.3) return dpsExplicit to "Soma dos proventos (12 meses)"
                }
                return null
            }
            return dpsFromYield to "LTM via cotação × Dividend Yield ${"%.1f".format(dyPct)}%"
        }

        if (explicitOk) {
            if (price != null && price > 0) {
                val impliedYield = (dpsExplicit!! / price) * 100.0
                if (impliedYield < 0.3) return null
                if (impliedYield > 40.0) return null
            }
            return dpsExplicit!! to "Soma dos proventos (12 meses)"
        }

        return null
    }

    /** Margem % = (régua − cotação) / régua × 100. Positiva = cotação abaixo da régua. */
    fun safetyMargin(fair: Double?, price: Double?): Double? {
        if (fair == null || price == null || fair <= 0) return null
        return ((fair - price) / fair) * 100.0
    }

    /**
     * Normaliza DY para percentual (ex.: 0,08 → 8%).
     * Descarta valores absurdos (> 80% a.a.) de unidade errada da API.
     */
    private fun normalizeYieldPercent(raw: Double?): Double? {
        if (raw == null || raw <= 0) return null
        // APIs misturam: 0,07 (=7%), 7,0 (=7%) e às vezes 0,7 (=0,7% já em pontos).
        // Regra:
        //  > 1     → já é percentual (ex.: 7,0)
        //  ≤ 0,25  → fração (ex.: 0,05 → 5%)
        //  0,25–1  → percentual baixo já convertido (ex.: 0,7 → 0,7%), NÃO ×100
        val pct = when {
            raw > 1.0 -> raw
            raw <= 0.25 -> raw * 100.0
            else -> raw
        }
        // DY acima de 40% a.a. na B3 quase sempre é lixo de API
        if (pct > 40.0) return null
        return pct
    }


    // ─── Peter Lynch (estimativa por PEG) ───────────────────────────────────

    data class LynchBreakdown(
        val estimatePrice: Double,
        val lpa: Double,
        val growthPercent: Double,
        val peg: Double?,
        val pl: Double?,
        val formula: String = "LPA × crescimento % esperado (PEG ≈ 1)",
        val note: String = "Estimativa inspirada em Peter Lynch (PEG). Não é preço oficial. Crescimento limitado a 5–25% a.a. para evitar distorções."
    )

    /**
     * Estimativa Lynch: com PEG=1, P/L justo ≈ taxa de crescimento do lucro (%).
     * Preço estimado ≈ LPA × g (g em pontos percentuais, ex.: 12 → P/L 12).
     */
    fun lynchBreakdown(
        lpa: Double?,
        price: Double?,
        pl: Double?,
        profitGrowthPercent: Double?,
        revenueGrowthPercent: Double? = null
    ): LynchBreakdown? {
        val eps = when {
            lpa != null && lpa > 0 -> lpa
            price != null && pl != null && pl > 0 -> price / pl
            else -> return null
        }
        val gRaw = profitGrowthPercent ?: revenueGrowthPercent ?: return null
        // g em % a.a.; limita para não gerar preços absurdos
        val g = gRaw.coerceIn(5.0, 25.0)
        val estimate = eps * g
        if (estimate <= 0 || estimate.isNaN()) return null
        val peg = if (pl != null && pl > 0 && g > 0) pl / g else null
        return LynchBreakdown(
            estimatePrice = estimate,
            lpa = eps,
            growthPercent = g,
            peg = peg,
            pl = pl
        )
    }

    fun lynchEstimate(
        lpa: Double?, price: Double?, pl: Double?,
        profitGrowth: Double?, revenueGrowth: Double? = null
    ): Double? = lynchBreakdown(lpa, price, pl, profitGrowth, revenueGrowth)?.estimatePrice

    // ─── Barsi (estratégia de dividendos — sem fórmula oficial única) ───────

    data class BarsiBreakdown(
        val estimatePrice: Double,
        val dividendsPerShare: Double,
        val yieldTargetPercent: Double,
        val formula: String,
        val note: String = "Estimativa baseada em critérios de renda (DY sustentável). Não existe fórmula oficial pública única atribuída a Luiz Barsi."
    )

    /**
     * Estimativa de entrada por renda: DPS_LTM ÷ yield-alvo.
     * Yield-alvo padrão 6% (alinhado à lógica de renda; configurável).
     */
    fun barsiBreakdown(
        price: Double?,
        dividendYieldPercent: Double?,
        annualDividendsPerShare: Double? = null,
        yieldTargetPercent: Double = 6.0
    ): BarsiBreakdown? {
        val bazin = bazinBreakdown(price, dividendYieldPercent, annualDividendsPerShare) ?: return null
        val target = yieldTargetPercent.coerceIn(4.0, 10.0)
        val estimate = bazin.dividendsPerShare / (target / 100.0)
        if (estimate <= 0) return null
        return BarsiBreakdown(
            estimatePrice = estimate,
            dividendsPerShare = bazin.dividendsPerShare,
            yieldTargetPercent = target,
            formula = "DPS anual ÷ ${(target/100).let { "%.2f".format(it) }} (yield-alvo ${"%.0f".format(target)}%)"
        )
    }

    fun barsiEstimate(
        price: Double?, dy: Double?, annualDps: Double? = null, yieldTarget: Double = 6.0
    ): Double? = barsiBreakdown(price, dy, annualDps, yieldTarget)?.estimatePrice

    // ─── DCF simplificado (Gordon a partir de LPA como proxy de FCF/ação) ───

    data class DcfBreakdown(
        val estimatePrice: Double,
        val cashFlowPerShare: Double,
        val growthPercent: Double,
        val discountPercent: Double,
        val terminalGrowthPercent: Double,
        val formula: String = "CF₁ / (r − g)  (modelo de Gordon simplificado)",
        val note: String = "DCF simplificado: usa LPA como proxy de fluxo por ação quando FCF não está disponível. Taxa de desconto e crescimento são premissas — altere mentalmente conforme seu cenário."
    )

    /**
     * Valor ≈ CF0×(1+g) / (r−g), com r > g.
     * CF0 padrão = LPA (proxy); g = crescimento do lucro (limitado); r = 10% a.a. default.
     */
    fun dcfBreakdown(
        lpa: Double?,
        price: Double?,
        pl: Double?,
        profitGrowthPercent: Double?,
        discountPercent: Double = 10.0,
        terminalGrowthPercent: Double = 3.0
    ): DcfBreakdown? {
        val cf0 = when {
            lpa != null && lpa > 0 -> lpa
            price != null && pl != null && pl > 0 -> price / pl
            else -> return null
        }
        val g = (profitGrowthPercent ?: 5.0).coerceIn(0.0, 12.0) / 100.0
        val r = discountPercent.coerceIn(6.0, 20.0) / 100.0
        val gt = terminalGrowthPercent.coerceIn(0.0, 5.0) / 100.0
        if (r <= g) return null
        val cf1 = cf0 * (1.0 + g)
        val value = cf1 / (r - gt)
        if (value <= 0 || value.isNaN() || value.isInfinite()) return null
        return DcfBreakdown(
            estimatePrice = value,
            cashFlowPerShare = cf0,
            growthPercent = g * 100.0,
            discountPercent = r * 100.0,
            terminalGrowthPercent = gt * 100.0
        )
    }

    fun dcfEstimate(
        lpa: Double?, price: Double?, pl: Double?, growth: Double?,
        discount: Double = 10.0, terminal: Double = 3.0
    ): Double? = dcfBreakdown(lpa, price, pl, growth, discount, terminal)?.estimatePrice

    /** (estimado − preço) / preço × 100. Positivo = cotação abaixo da estimativa (desconto). */
    fun discountVsPrice(estimate: Double?, price: Double?): Double? {
        if (estimate == null || price == null || price <= 0) return null
        return ((estimate - price) / price) * 100.0
    }
}
