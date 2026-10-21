package com.b3insights.app.util

import kotlin.math.abs
import kotlin.math.pow

/**
 * Cálculos de rentabilidade alinhados ao que o mercado (e o Léo / Geração Dividendos) usa:
 *
 * - **TIR (IRR / XIRR)**: ponderada pelo dinheiro — considera datas e valores dos aportes
 *   e dos proventos. Responde: "quanto rendeu o meu dinheiro de fato?"
 *
 * - **TWR (Time-Weighted Return)**: ponderada pelo tempo — remove o efeito do timing
 *   dos aportes. Responde: "como o ativo/carteira performou, independente de eu ter
 *   aportado mais ou menos em determinado momento?"
 *
 * Nunca anualize dividindo o retorno total pelo número de anos (juros simples).
 * Use juros compostos: (1 + R)^(1/anos) − 1
 */
object PortfolioReturnCalculator {

    data class CashFlow(val dateMs: Long, val amount: Double)

    data class ReturnResult(
        val invested: Double,
        val currentValue: Double,
        val dividendsTotal: Double,
        /** (atual − investido) / investido — só variação de preço, incompleto */
        val simplePriceReturnPct: Double?,
        /** (atual − investido + proventos) / investido */
        val totalReturnPct: Double?,
        /** TIR anualizada (XIRR), % a.a. */
        val tirAnnualPct: Double?,
        /** TWR acumulado no período, % */
        val twrTotalPct: Double?,
        /** TWR anualizado, % a.a. */
        val twrAnnualPct: Double?,
        val years: Double
    )

    /** Anualização por juros compostos. totalReturn é decimal (ex.: 0.1495 = 14,95%). */
    fun annualizeCompound(totalReturn: Double, years: Double): Double? {
        if (years <= 0.0 || totalReturn <= -1.0) return null
        return ( (1.0 + totalReturn).pow(1.0 / years) - 1.0 ) * 100.0
    }

    /**
     * XIRR / TIR: resolve NPV = 0 para a taxa diária, depois anualiza.
     * Fluxos: aportes negativos, proventos positivos, valor atual positivo no final.
     */
    fun xirr(flows: List<CashFlow>, guess: Double = 0.1): Double? {
        if (flows.size < 2) return null
        val sorted = flows.sortedBy { it.dateMs }
        val t0 = sorted.first().dateMs
        // dias desde o primeiro fluxo
        fun day(f: CashFlow) = (f.dateMs - t0) / 86_400_000.0

        // Newton-Raphson em taxa anual
        var r = guess
        repeat(64) {
            var npv = 0.0
            var dNpv = 0.0
            for (f in sorted) {
                val t = day(f) / 365.0
                val df = (1.0 + r).pow(-t)
                npv += f.amount * df
                dNpv += f.amount * (-t) * (1.0 + r).pow(-t - 1.0)
            }
            if (abs(dNpv) < 1e-12) return null
            val next = r - npv / dNpv
            if (abs(next - r) < 1e-10) {
                return if (next.isFinite() && next > -0.99) next * 100.0 else null
            }
            r = next
            if (!r.isFinite() || r <= -0.99) return null
        }
        return if (r.isFinite() && r > -0.99) r * 100.0 else null
    }

    /**
     * TWR clássico: encadeia retornos de subperíodos entre fluxos externos.
     * [marks] = valor da carteira logo antes de cada fluxo (exceto o final).
     *
     * Forma prática com lista (date, valueBeforeFlow, externalFlow):
     * HP_i = (V_end_i - V_start_i - CF_i) / (V_start_i)  quando CF no fim do subperíodo
     *
     * Implementação: dada série de (timestamp, portfolioValue) e lista de external CF
     * nos mesmos timestamps, calcula sub-period returns.
     */
    fun twr(
        points: List<Pair<Long, Double>>, // chronological portfolio MV
        externalFlows: List<CashFlow> // only external (buys/sells), not dividends if reinvested in MV
    ): Double? {
        if (points.size < 2) return null
        val sortedPts = points.sortedBy { it.first }
        val flowByDay = externalFlows.groupBy { it.dateMs / 86_400_000L }
            .mapValues { (_, v) -> v.sumOf { it.amount } }

        var factor = 1.0
        for (i in 1 until sortedPts.size) {
            val (tPrev, vPrev) = sortedPts[i - 1]
            val (tNow, vNow) = sortedPts[i]
            // fluxo no início do subperíodo (após vPrev): aportes aumentam base
            val dayKey = tNow / 86_400_000L
            val cf = flowByDay[dayKey] ?: 0.0
            // Base no início do período = valor anterior; se houve aporte no instante,
            // a convenção Modified Dietz / TWR de subperíodo:
            // r = (V_fim - V_ini - CF) / (V_ini)  com CF = aporte líquido no período
            // Quando só temos snapshots, CF entre snapshots entra no denominador ponderado.
            val start = vPrev
            if (start <= 0) continue
            // Aporte entre prev e now: se cf < 0 (compra), o valor "fim" já inclui o ativo comprado
            // r = (vNow - start - (-cf_buy?)) 
            // external buy is negative cash (money out). Portfolio value jumps by |cf|.
            // Standard: r = (EMV - BMV - C) / BMV where C is net external flow into portfolio (+)
            val netIn = -cf // buy -1000 cash => +1000 into portfolio
            val r = (vNow - start - netIn) / start
            if (r.isFinite()) factor *= (1.0 + r)
        }
        val total = factor - 1.0
        return if (total.isFinite()) total * 100.0 else null
    }

    /**
     * Modified Dietz — boa aproximação do TWR quando não há mark diário em todo aporte.
     * R = (EMV − BMV − CF) / (BMV + Σ (CF_i × w_i))
     * w_i = proporção do período em que o aporte ficou investido.
     */
    fun modifiedDietz(
        beginValue: Double,
        endValue: Double,
        flows: List<CashFlow>,
        periodStartMs: Long,
        periodEndMs: Long
    ): Double? {
        val days = (periodEndMs - periodStartMs).toDouble()
        if (days <= 0 || beginValue < 0) return null
        val netCf = flows.sumOf { it.amount } // aportes negativos no nosso sign convention
        // Para Dietz, CF positivo = dinheiro que ENTRA na carteira
        val inflows = flows.map { -it.amount to it.dateMs } // invert sign: buy becomes positive into port
        val weighted = inflows.sumOf { (amt, date) ->
            val w = ((periodEndMs - date).toDouble() / days).coerceIn(0.0, 1.0)
            amt * w
        }
        val denom = beginValue + weighted
        if (abs(denom) < 1e-9) return null
        // end - begin - netIntoPortfolio; netInto = -sum(our negative buys) = -netCf
        val netInto = -netCf
        val r = (endValue - beginValue - netInto) / denom
        return if (r.isFinite()) r * 100.0 else null
    }

    fun yearsBetween(startMs: Long, endMs: Long): Double {
        return ((endMs - startMs).toDouble() / (365.25 * 86_400_000.0)).coerceAtLeast(1.0 / 365.0)
    }
}
