package com.b3insights.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.billing.ProConfig
import com.b3insights.app.billing.ProManager
import com.b3insights.app.data.local.entity.PortfolioPositionEntity
import com.b3insights.app.data.model.ChartPeriod
import com.b3insights.app.data.model.DataResult
import com.b3insights.app.util.quoteTickerFor
import com.b3insights.app.util.PortfolioReturnCalculator
import com.b3insights.app.util.PortfolioCalculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/** Posição agregada por ticker, já somando as múltiplas linhas de aporte (preço médio ponderado). */
data class PortfolioItemUi(
    val ticker: String,
    val assetType: String,
    val totalQuantity: Double,
    val averagePrice: Double,
    val totalInvested: Double,
    val currentPrice: Double?,
    val currentValue: Double?,
    val profitLoss: Double?,
    val profitLossPercent: Double?,
    val lastPurchaseDate: Long?,
    val positions: List<PortfolioPositionEntity>,
    /** P/VP quando a fonte retornar (ações/FIIs). */
    val pvp: Double? = null,
    /** Dividend yield atual % (sobre cotação). */
    val dividendYield: Double? = null,
    /**
     * Yield on cost % ≈ (renda anual estimada) / valor investido × 100.
     * Renda anual ≈ valor atual × (DY/100) quando DY disponível.
     */
    val yieldOnCost: Double? = null,
    val pl: Double? = null
)

data class PortfolioUiState(
    val isLoading: Boolean = true,
    val items: List<PortfolioItemUi> = emptyList(),
    val totalInvested: Double = 0.0,
    val totalCurrent: Double = 0.0,
    /** TIR anualizada (XIRR) da carteira em % a.a. — aportes, vendas e valor atual */
    val tirAnnualPct: Double? = null,
    /** true se USD/BRL falhou e usamos 5,0 */
    val fxFallback: Boolean = false,
    val errorMessage: String? = null
)

class PortfolioViewModel : ViewModel() {
    private val portfolioRepository = B3InsightsApp.instance.portfolioRepository
    private val marketDataRepository = B3InsightsApp.instance.marketDataRepository

    private val _uiState = MutableStateFlow(PortfolioUiState())
    val uiState: StateFlow<PortfolioUiState> = _uiState.asStateFlow()
    private var lastPositions: List<PortfolioPositionEntity> = emptyList()

    init {
        viewModelScope.launch {
            portfolioRepository.getAllPositions().collect { positions ->
                lastPositions = positions
                buildUiState(positions)
            }
        }
    }

    private suspend fun buildUiState(positions: List<PortfolioPositionEntity>) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        val lots = PortfolioCalculator.aggregate(positions)
        val items = mutableListOf<PortfolioItemUi>()

        var usdBrl = 5.0
        var fxFallback = true
        when (val fx = marketDataRepository.getQuote("USDBRL=X")) {
            is DataResult.Success -> fx.data.currentPrice?.takeIf { it > 0 }?.let {
                usdBrl = it
                fxFallback = false
            }
            else -> Unit
        }

        data class ExtraMetrics(val price: Double?, val pvp: Double?, val dy: Double?, val pl: Double?)

        val metricsMap = coroutineScope {
            lots.map { lot ->
                async {
                    val qTicker = quoteTickerFor(lot.ticker, lot.assetType)
                    var price: Double? = null
                    var pvp: Double? = null
                    var dy: Double? = null
                    var pl: Double? = null
                    when (val result = marketDataRepository.getQuote(qTicker)) {
                        is DataResult.Success -> price = result.data.currentPrice
                        else -> Unit
                    }
                    if (lot.assetType != "CRIPTO") {
                        when (val d = marketDataRepository.getAssetDetail(lot.ticker, ChartPeriod.Y1, forceRefresh = false)) {
                            is DataResult.Success -> {
                                val ind = d.data.indicators
                                if (price == null) price = d.data.quote.currentPrice
                                pvp = ind.pvp
                                dy = ind.dividendYield
                                pl = ind.pl
                            }
                            else -> Unit
                        }
                    }
                    lot.ticker to ExtraMetrics(price, pvp, dy, pl)
                }
            }.awaitAll().toMap()
        }

        val currentByTicker = mutableMapOf<String, Double>()
        for (lot in lots) {
            val isCrypto = lot.assetType == "CRIPTO" ||
                lot.ticker.contains("-USD") || lot.ticker.contains("-BRL")
            val m = metricsMap[lot.ticker]
            val currentPrice = m?.price
            val currentValue = currentPrice?.let { px ->
                if (isCrypto) px * lot.quantity * usdBrl else px * lot.quantity
            }
            if (currentValue != null) {
                currentByTicker[lot.ticker.uppercase()] = currentValue
            }
            val profitLoss = currentValue?.let { it - lot.invested }
            val profitLossPercent = if (currentValue != null && lot.invested > 0) {
                ((currentValue - lot.invested) / lot.invested) * 100.0
            } else null
            val dy = m?.dy
            val yieldOnCost = if (dy != null && dy > 0 && currentValue != null && lot.invested > 0) {
                val annualIncome = currentValue * (normalizeDy(dy) / 100.0)
                (annualIncome / lot.invested) * 100.0
            } else null
            items.add(
                PortfolioItemUi(
                    ticker = lot.ticker,
                    assetType = lot.assetType,
                    totalQuantity = lot.quantity,
                    averagePrice = lot.averagePrice,
                    totalInvested = lot.invested,
                    currentPrice = currentPrice,
                    currentValue = currentValue,
                    profitLoss = profitLoss,
                    profitLossPercent = profitLossPercent,
                    lastPurchaseDate = lot.positions.maxOfOrNull { it.purchaseDate },
                    positions = lot.positions,
                    pvp = m?.pvp,
                    dividendYield = dy?.let { normalizeDy(it) },
                    yieldOnCost = yieldOnCost,
                    pl = m?.pl
                )
            )
        }

        val totalInvestedSum = items.sumOf { it.totalInvested }
        val totalCurrentSum = items.sumOf { it.currentValue ?: it.totalInvested }
        val flows = PortfolioCalculator.xirrCashFlows(positions, currentByTicker)
        val tir = PortfolioReturnCalculator.xirr(flows)

        _uiState.value = PortfolioUiState(
            isLoading = false,
            items = items.sortedByDescending { it.currentValue ?: it.totalInvested },
            totalInvested = totalInvestedSum,
            totalCurrent = totalCurrentSum,
            tirAnnualPct = tir,
            fxFallback = fxFallback && items.any {
                it.assetType == "CRIPTO" || it.ticker.contains("-USD") || it.ticker.contains("-BRL")
            }
        )
    }

    fun refreshQuotes() {
        viewModelScope.launch {
            if (lastPositions.isNotEmpty()) buildUiState(lastPositions)
        }
    }


    /** FREE: no máximo FREE_MAX_ASSETS tickers distintos. PRO ilimitado. */
    fun canAddAsset(ticker: String): Boolean = true // limite removido; Play Billing depois


    fun distinctAssetCount(): Int = lastPositions.map { it.ticker.uppercase() }.toSet().size

    fun addPosition(
        ticker: String,
        assetType: String,
        quantity: Double,
        purchasePrice: Double,
        purchaseDate: Long,
        notes: String?,
        tags: String? = null,
        side: String = "BUY",
        syncFinanceiro: Boolean = false,
        valorBrl: Double = 0.0
    ) {
        viewModelScope.launch {
            portfolioRepository.addPosition(
                ticker = ticker,
                assetType = assetType,
                quantity = quantity,
                averagePrice = purchasePrice,
                purchasePrice = purchasePrice,
                purchaseDate = purchaseDate,
                notes = notes,
                tags = tags,
                side = side,
                syncFinanceiro = syncFinanceiro,
                valorBrl = valorBrl
            )
        }
    }

    fun deletePosition(entity: PortfolioPositionEntity) {
        viewModelScope.launch { portfolioRepository.deletePosition(entity) }
    }

    /** Substitui todas as posições de um ticker por uma linha editada. */
    fun replacePosition(
        ticker: String,
        assetType: String,
        quantity: Double,
        purchasePrice: Double,
        purchaseDate: Long,
        notes: String?,
        oldPositions: List<PortfolioPositionEntity>,
        tags: String? = null,
        side: String = "BUY"
    ) {
        viewModelScope.launch {
            oldPositions.forEach { portfolioRepository.deletePosition(it) }
            portfolioRepository.addPosition(
                ticker = ticker,
                assetType = assetType,
                quantity = quantity,
                averagePrice = purchasePrice,
                purchasePrice = purchasePrice,
                purchaseDate = purchaseDate,
                notes = notes,
                tags = tags,
                side = side
            )
        }
    }

    fun deleteAllForTicker(positions: List<PortfolioPositionEntity>) {
        viewModelScope.launch {
            positions.forEach { portfolioRepository.deletePosition(it) }
        }
    }
}

/** DY às vezes vem 0.08 (=8%) ou 8.0 (=8%). Normaliza para pontos percentuais. */
private fun normalizeDy(raw: Double): Double = when {
    raw <= 0 -> raw
    raw <= 0.25 -> raw * 100.0
    else -> raw
}
