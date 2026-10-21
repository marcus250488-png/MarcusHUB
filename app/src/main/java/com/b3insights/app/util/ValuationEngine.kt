package com.b3insights.app.util

import com.b3insights.app.data.model.FieldStatus
import com.b3insights.app.data.model.FieldValue

/**
 * Motor de valuation multimétodo.
 * Documenta fórmula, entradas e status — nunca inventa número.
 *
 * Fontes de entrada esperadas: cotação + indicadores já consolidados
 * (brapi/Yahoo via RemoteDataSource). CVM/Gemini ficam no backend quando deployados.
 */
object ValuationEngine {

    data class MethodResult(
        val methodId: String,
        val title: String,
        val estimate: FieldValue,
        /** (estimativa − preço) / preço × 100; positivo = cotação abaixo da estimativa */
        val discountPercent: FieldValue,
        val explanation: String
    )

    data class Snapshot(
        val ticker: String,
        val price: FieldValue,
        val graham: MethodResult,
        val bazin: MethodResult,
        val lynch: MethodResult,
        val barsi: MethodResult,
        val dcf: MethodResult,
        val computedAt: Long = System.currentTimeMillis()
    )

    fun compute(
        ticker: String,
        price: Double?,
        lpa: Double?,
        vpa: Double?,
        pl: Double?,
        pvp: Double?,
        dividendYieldPercent: Double?,
        annualDividendsPerShare: Double?,
        profitGrowthPercent: Double?,
        revenueGrowthPercent: Double?,
        bazinRequiredYield: Double = 0.06
    ): Snapshot {
        val priceField = if (price != null && price > 0) {
            FieldValue.available(price, source = "market", sourceType = "BRAPI/YAHOO")
        } else {
            FieldValue.unavailable("Cotação indisponível")
        }

        val grahamBd = Valuation.grahamBreakdown(lpa, vpa, price, pl, pvp)
        val grahamEst = if (grahamBd != null) {
            FieldValue.calculated(
                value = grahamBd.fairPrice,
                formula = grahamBd.formula,
                inputs = mapOf("LPA" to grahamBd.lpa, "VPA" to grahamBd.vpa, "22.5" to 22.5),
                note = grahamBd.note + " | LPA: ${grahamBd.lpaSource}; VPA: ${grahamBd.vpaSource}"
            )
        } else {
            FieldValue.unavailable("Dados insuficientes para calcular Graham (faltam LPA e/ou VPA).")
        }

        val bazinBd = Valuation.bazinBreakdown(price, dividendYieldPercent, annualDividendsPerShare)
        val bazinEst = if (bazinBd != null) {
            // Recalcula com yield configurável se diferente de 6%
            val ceiling = if (kotlin.math.abs(bazinRequiredYield - 0.06) > 1e-9) {
                bazinBd.dividendsPerShare / bazinRequiredYield
            } else bazinBd.ceilingPrice
            FieldValue.calculated(
                value = ceiling,
                formula = "DPS anual ÷ ${"%.2f".format(bazinRequiredYield)} (yield-alvo ${"%.1f".format(bazinRequiredYield * 100)}%)",
                inputs = mapOf(
                    "DPS_anual" to bazinBd.dividendsPerShare,
                    "yield_alvo" to bazinRequiredYield
                ),
                note = "${bazinBd.note} Período: ${bazinBd.dividendPeriod}"
            )
        } else {
            FieldValue.unavailable("Dados insuficientes para Bazin (faltam proventos 12m / DY).")
        }

        val lynchBd = Valuation.lynchBreakdown(lpa, price, pl, profitGrowthPercent, revenueGrowthPercent)
        val lynchEst = if (lynchBd != null) {
            FieldValue.calculated(
                value = lynchBd.estimatePrice,
                formula = lynchBd.formula,
                inputs = mapOf("LPA" to lynchBd.lpa, "crescimento_%" to lynchBd.growthPercent, "P/L" to lynchBd.pl),
                note = lynchBd.note
            )
        } else {
            FieldValue.unavailable("Dados insuficientes para estimativa Lynch (faltam LPA e/ou crescimento).")
        }

        val barsiBd = Valuation.barsiBreakdown(price, dividendYieldPercent, annualDividendsPerShare, bazinRequiredYield * 100)
        val barsiEst = if (barsiBd != null) {
            FieldValue.calculated(
                value = barsiBd.estimatePrice,
                formula = barsiBd.formula,
                inputs = mapOf("DPS_anual" to barsiBd.dividendsPerShare, "yield_alvo_%" to barsiBd.yieldTargetPercent),
                note = barsiBd.note
            )
        } else {
            FieldValue.unavailable("Dados insuficientes para estimativa de renda (Barsi).")
        }

        val dcfBd = Valuation.dcfBreakdown(lpa, price, pl, profitGrowthPercent)
        val dcfEst = if (dcfBd != null) {
            FieldValue.calculated(
                value = dcfBd.estimatePrice,
                formula = dcfBd.formula,
                inputs = mapOf(
                    "CF_por_ação" to dcfBd.cashFlowPerShare,
                    "g_%" to dcfBd.growthPercent,
                    "r_%" to dcfBd.discountPercent,
                    "g_terminal_%" to dcfBd.terminalGrowthPercent
                ),
                note = dcfBd.note
            )
        } else {
            FieldValue.unavailable("Dados insuficientes para DCF simplificado.")
        }

        fun method(id: String, title: String, est: FieldValue, explanation: String): MethodResult {
            val disc = Valuation.discountVsPrice(est.displayOrNull(), price)
            val discField = if (disc != null) {
                FieldValue.calculated(
                    value = disc,
                    formula = "(estimativa − preço) / preço × 100",
                    inputs = mapOf("estimativa" to est.displayOrNull(), "preço" to price)
                )
            } else FieldValue.unavailable()
            return MethodResult(id, title, est, discField, explanation)
        }

        return Snapshot(
            ticker = ticker,
            price = priceField,
            graham = method("graham", "Benjamin Graham", grahamEst,
                "Régua clássica √(22,5×LPA×VPA). Não é preço oficial universal."),
            bazin = method("bazin", "Décio Bazin (preço-teto)", bazinEst,
                "Preço-teto por yield-alvo configurável sobre proventos 12m."),
            lynch = method("lynch", "Peter Lynch (estimativa PEG)", lynchEst,
                "Estimativa inspirada em PEG≈1 — não é fórmula oficial única."),
            barsi = method("barsi", "Critérios de renda (Barsi)", barsiEst,
                "Estimativa por DPS e yield-alvo — sem fórmula oficial pública única."),
            dcf = method("dcf", "DCF simplificado (Gordon)", dcfEst,
                "Modelo simplificado; premissas de desconto e crescimento explícitas.")
        )
    }
}
