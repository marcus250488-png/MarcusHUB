package com.b3insights.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.data.model.AssetQuote
import com.b3insights.app.data.model.AssetType
import com.b3insights.app.data.model.DataResult
import com.b3insights.app.util.PortfolioCalculator
import com.b3insights.app.util.quoteTickerFor
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** Resumo do cartão azul "Meus Ativos" da Home. */
data class PortfolioSummary(
    val totalValue: Double = 0.0,
    /** Variação do dia da carteira, em %. */
    val dayChangePercent: Double = 0.0,
    val acoes: Int = 0,
    val fiis: Int = 0,
    val etfs: Int = 0,
    val cripto: Int = 0
) {
    val totalAssets get() = acoes + fiis + etfs + cripto
}

data class HomeUiState(
    val summary: PortfolioSummary = PortfolioSummary(),
    /** Ativos da carteira (ordenados por valor); vazio se a carteira estiver vazia. */
    val myAssets: List<AssetQuote> = emptyList(),
    /** "Destaques do Momento": cotações dos ativos mais pesquisados. */
    val highlights: List<AssetQuote> = emptyList(),
    val query: String = "",
    val searchResults: List<AssetQuote> = emptyList(),
    val recentSearches: List<String> = emptyList(),
    val isSearching: Boolean = false,
    val errorMessage: String? = null
)

class HomeViewModel : ViewModel() {
    private val repository = B3InsightsApp.instance.marketDataRepository

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val portfolioRepository = B3InsightsApp.instance.portfolioRepository
    private var lastPositions: List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> = emptyList()

    private companion object {
        val HIGHLIGHT_TICKERS = listOf(
            "ITUB4", "PETR4", "VALE3", "MXRF11", "WEGE3", "BBAS3", "HGLG11", "BOVA11"
        )
    }

    init {
        viewModelScope.launch {
            repository.getRecentSearches().collect { recents ->
                _uiState.value = _uiState.value.copy(recentSearches = recents)
            }
        }
        // Cartão azul + lista "Meus Ativos": recalcula sempre que a carteira muda
        viewModelScope.launch {
            portfolioRepository.getAllPositions().collect { positions ->
                loadPortfolio(positions)
            }
        }
        refreshHighlights()
    }

    /** Atualiza cotações (chamado ao abrir a Home). */
    fun refresh() {
        refreshHighlights()
        viewModelScope.launch { loadPortfolio(lastPositions) }
    }

    private fun refreshHighlights() {
        viewModelScope.launch {
            val quotes = coroutineScope {
                HIGHLIGHT_TICKERS.map { t -> async { (repository.getQuote(t) as? DataResult.Success)?.data } }
                    .awaitAll()
            }.filterNotNull()
            if (quotes.isNotEmpty()) {
                _uiState.value = _uiState.value.copy(highlights = quotes)
            }
        }
    }

    private suspend fun loadPortfolio(positions: List<com.b3insights.app.data.local.entity.PortfolioPositionEntity>) {
        lastPositions = positions
        val lots = PortfolioCalculator.aggregate(positions)
        if (lots.isEmpty()) {
            _uiState.value = _uiState.value.copy(summary = PortfolioSummary(), myAssets = emptyList())
            return
        }
        fun isCrypto(type: String, ticker: String) =
            type == AssetType.CRIPTO.name || ticker.contains("-USD") || ticker.contains("-BRL")

        var usdBrl = 5.0
        if (lots.any { isCrypto(it.assetType, it.ticker) }) {
            (repository.getQuote("USDBRL=X") as? DataResult.Success)?.data?.currentPrice
                ?.takeIf { it > 0 }?.let { usdBrl = it }
        }
        val quotes = coroutineScope {
            lots.map { lot ->
                async {
                    lot to (repository.getQuote(quoteTickerFor(lot.ticker, lot.assetType)) as? DataResult.Success)?.data
                }
            }.awaitAll()
        }
        var total = 0.0
        var dayDelta = 0.0
        var acoes = 0; var fiis = 0; var etfs = 0; var cripto = 0
        val assets = mutableListOf<Pair<Double, AssetQuote>>()
        for ((lot, q) in quotes) {
            val crypto = isCrypto(lot.assetType, lot.ticker)
            when {
                crypto -> cripto++
                lot.assetType == AssetType.FII.name -> fiis++
                lot.assetType == AssetType.ETF.name -> etfs++
                else -> acoes++
            }
            val px = q?.currentPrice
            val value = if (px != null) px * lot.quantity * (if (crypto) usdBrl else 1.0) else lot.invested
            total += value
            val chg = q?.dayChangePercent
            if (chg != null && chg > -100.0) dayDelta += value * chg / (100.0 + chg)
            val base = q ?: AssetQuote(
                ticker = lot.ticker, name = null, assetType = AssetType.fromApiType(lot.assetType),
                sector = null, currentPrice = null, dayChange = null, dayChangePercent = null,
                dayHigh = null, dayLow = null, volume = null, marketCap = null, lastUpdated = null
            )
            // mantém o ticker da carteira (ex.: BTC-BRL) para abrir o ativo certo
            assets += value to base.copy(ticker = lot.ticker)
        }
        val prev = total - dayDelta
        val pct = if (prev > 0) dayDelta / prev * 100.0 else 0.0
        _uiState.value = _uiState.value.copy(
            summary = PortfolioSummary(total, pct, acoes, fiis, etfs, cripto),
            myAssets = assets.sortedByDescending { it.first }.map { it.second }
        )
    }

    fun onQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(query = query)
    }

    fun search() {
        val query = _uiState.value.query.trim()
        if (query.isEmpty()) return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSearching = true, errorMessage = null)
            when (val result = repository.searchAssets(query)) {
                is DataResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isSearching = false,
                        searchResults = result.data
                    )
                }
                is DataResult.Error -> {
                    _uiState.value = _uiState.value.copy(isSearching = false, errorMessage = result.message)
                }
                DataResult.Loading -> Unit
            }
        }
    }

    fun clearResults() {
        _uiState.value = _uiState.value.copy(searchResults = emptyList(), query = "")
    }
}
