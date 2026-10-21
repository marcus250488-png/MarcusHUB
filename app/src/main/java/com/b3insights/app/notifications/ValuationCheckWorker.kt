package com.b3insights.app.notifications

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.data.model.ChartPeriod
import com.b3insights.app.data.model.DataResult
import com.b3insights.app.util.Valuation
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withTimeoutOrNull

/**
 * A cada ~5h: analisa universo enxuto (carteira + blue chips) com concorrência limitada.
 * Auditoria C-10 — evita 40 chamadas sequenciais e rate limit.
 */
class ValuationCheckWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val app = B3InsightsApp.instance
        val settings = app.database.userSettingsDao().getSettings().first()
        if (settings?.notificationsEnabled == false) return Result.success()

        val repo = app.marketDataRepository

        val portfolioTickers = runCatching {
            app.database.portfolioDao().getDistinctTickers().map { it.uppercase() }
        }.getOrDefault(emptyList())

        // Blue chips líquidas (não 40 tickers)
        val liquid = listOf(
            "PETR4", "VALE3", "ITUB4", "BBDC4", "BBAS3", "ABEV3", "WEGE3", "RENT3",
            "B3SA3", "PRIO3", "EQTL3", "TAEE11", "EGIE3", "CMIG4", "SANB11"
        )
        val universe = (portfolioTickers + liquid).distinct().take(20)

        data class Pick(
            val ticker: String,
            val price: Double,
            val ceiling: Double,
            val margin: Double
        )

        val picks = withTimeoutOrNull(90_000L) {
            coroutineScope {
                val sem = Semaphore(4) // no máximo 4 requisições em paralelo
                universe.map { ticker ->
                    async {
                        sem.withPermit {
                            try {
                                val quote = when (val q = repo.getQuote(ticker)) {
                                    is DataResult.Success -> q.data
                                    else -> return@withPermit null
                                }
                                val price = quote.currentPrice ?: return@withPermit null
                                if (price <= 0) return@withPermit null

                                // Só detalhe se a cotação for razoável (evita detalhe inútil)
                                val ind = when (val d = repo.getAssetDetail(ticker, ChartPeriod.Y1)) {
                                    is DataResult.Success -> d.data.indicators
                                    else -> null
                                }
                                val bazin = Valuation.bazinCeiling(
                                    price = price,
                                    dividendYieldPercent = ind?.dividendYield,
                                    annualDividendsPerShare = ind?.dividendPerShare
                                ) ?: return@withPermit null

                                if (price >= bazin) return@withPermit null
                                val margin = Valuation.safetyMargin(bazin, price) ?: return@withPermit null
                                if (margin <= 0) return@withPermit null
                                Pick(ticker, price, bazin, margin)
                            } catch (_: Exception) {
                                null
                            }
                        }
                    }
                }.awaitAll().filterNotNull()
            }
        } ?: emptyList()

        val top5 = picks.sortedByDescending { it.margin }.take(5)
        if (top5.isEmpty()) return Result.success()

        val lines = top5.map {
            "%s · cotação R$ %.2f · teto R$ %.2f (margem %.1f%%)".format(
                it.ticker, it.price, it.ceiling, it.margin
            )
        }
        val body = "Método Bazin (DPS 12m / 0,06):\n" + lines.joinToString("\n")

        ValuationNotifier.show(
            applicationContext,
            id = 2100,
            title = "Top 5 ações abaixo do teto Bazin",
            body = body
        )
        return Result.success()
    }
}
