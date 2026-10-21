package com.b3insights.app.util

import com.b3insights.app.data.local.entity.PortfolioPositionEntity

/**
 * Cálculo único da carteira (auditoria: evitar 6 cópias divergentes).
 * Método de custo médio: vendas reduzem quantidade e custo proporcionalmente.
 */
object PortfolioCalculator {

    data class LotResult(
        val ticker: String,
        val assetType: String,
        val quantity: Double,
        val averagePrice: Double,
        /** Custo remanescente das compras ainda em carteira (após vendas). */
        val invested: Double,
        val positions: List<PortfolioPositionEntity>
    )

    data class FxQuote(
        val rate: Double,
        val isFallback: Boolean
    )

    /**
     * Agrupa por ticker e aplica compras/vendas em ordem de data.
     * Posições com qty residual <= 0 são omitidas.
     */
    fun aggregate(positions: List<PortfolioPositionEntity>): List<LotResult> {
        val grouped = positions.groupBy { it.ticker.uppercase() }
        val out = mutableListOf<LotResult>()
        for ((ticker, rows) in grouped) {
            val ordered = rows.sortedWith(
                compareBy<PortfolioPositionEntity> { it.purchaseDate }
                    .thenBy { if (it.side.equals("SELL", true)) 1 else 0 }
                    .thenBy { it.id }
            )
            var qty = 0.0
            var cost = 0.0
            for (p in ordered) {
                val side = p.side.uppercase()
                val q = p.quantity.coerceAtLeast(0.0)
                val px = p.averagePrice.takeIf { it > 0 } ?: p.purchasePrice
                if (side == "SELL") {
                    if (qty <= 0.0 || q <= 0.0) continue
                    val sold = q.coerceAtMost(qty)
                    val avg = if (qty > 0) cost / qty else 0.0
                    qty -= sold
                    cost -= avg * sold
                    if (cost < 0) cost = 0.0
                } else {
                    // BUY (default)
                    qty += q
                    cost += q * px
                }
            }
            if (qty <= 1e-12) continue
            val avg = cost / qty
            out += LotResult(
                ticker = rows.first().ticker,
                assetType = rows.first().assetType,
                quantity = qty,
                averagePrice = avg,
                invested = cost,
                positions = rows
            )
        }
        return out
    }

    /** Fluxos XIRR: compras negativas, vendas positivas (qty×preço), valor atual positivo no fim. */
    fun xirrCashFlows(
        positions: List<PortfolioPositionEntity>,
        currentValueByTicker: Map<String, Double>,
        nowMs: Long = System.currentTimeMillis()
    ): List<PortfolioReturnCalculator.CashFlow> {
        val flows = mutableListOf<PortfolioReturnCalculator.CashFlow>()
        for (p in positions) {
            val q = p.quantity.coerceAtLeast(0.0)
            val px = p.averagePrice.takeIf { it > 0 } ?: p.purchasePrice
            val amt = q * px
            if (amt <= 0 || p.purchaseDate <= 0) continue
            val side = p.side.uppercase()
            if (side == "SELL") {
                flows += PortfolioReturnCalculator.CashFlow(p.purchaseDate, amt)
            } else {
                flows += PortfolioReturnCalculator.CashFlow(p.purchaseDate, -amt)
            }
        }
        val totalCurrent = currentValueByTicker.values.sum()
        if (totalCurrent > 0 && flows.isNotEmpty()) {
            flows += PortfolioReturnCalculator.CashFlow(nowMs, totalCurrent)
        }
        return flows
    }
}
