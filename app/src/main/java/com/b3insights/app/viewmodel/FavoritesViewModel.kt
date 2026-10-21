package com.b3insights.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.data.local.entity.FavoriteAssetEntity
import com.b3insights.app.data.model.DataResult
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.sync.Mutex

data class FavoriteQuoteUi(
    val ticker: String,
    val name: String?,
    val assetType: String?,
    val price: Double?,
    val changePercent: Double?
)

class FavoritesViewModel : ViewModel() {
    private val app = B3InsightsApp.instance
    private val repository = app.marketDataRepository
    private val favoriteDao = app.database.favoriteAssetDao()

    /** Evita duas atualizações ao mesmo tempo (ex.: abrir a tela e o timer de 30s coincidirem). */
    private val refreshMutex = Mutex()

    val favorites: StateFlow<List<FavoriteAssetEntity>> = repository.getFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _live = MutableStateFlow<Map<String, FavoriteQuoteUi>>(emptyMap())
    val liveQuotes: StateFlow<Map<String, FavoriteQuoteUi>> = _live.asStateFlow()

    /** Horário da última atualização que realmente veio da rede (null = ainda não atualizou). */
    private val _lastUpdated = MutableStateFlow<Long?>(null)
    val lastUpdated: StateFlow<Long?> = _lastUpdated.asStateFlow()

    /**
     * Busca a cotação ATUAL de cada favorito (em paralelo) e guarda o resultado também no Room,
     * para o app mostrar o último preço real quando estiver sem internet.
     * A tela chama isto ao abrir e a cada 30s enquanto estiver visível.
     */
    suspend fun refreshQuotes() {
        if (!refreshMutex.tryLock()) return
        try {
            // Lê direto do banco: favorites.value pode ainda estar vazio quando a tela abre.
            val list = repository.getFavorites().first()
            if (list.isEmpty()) {
                _live.value = emptyMap()
                return
            }
            val results = coroutineScope {
                list.map { f -> async { f to repository.getQuote(f.ticker) } }.awaitAll()
            }
            val previous = _live.value
            val map = mutableMapOf<String, FavoriteQuoteUi>()
            var anyFresh = false
            for ((f, r) in results) {
                when (r) {
                    is DataResult.Success -> {
                        val q = r.data
                        if (!r.fromCache) anyFresh = true
                        val price = q.currentPrice
                        val change = q.dayChangePercent
                        map[f.ticker] = FavoriteQuoteUi(
                            ticker = f.ticker,
                            name = q.name ?: f.name,
                            assetType = f.assetType,
                            price = price ?: previous[f.ticker]?.price ?: f.lastKnownPrice,
                            changePercent = change ?: previous[f.ticker]?.changePercent
                                ?: f.lastKnownChangePercent
                        )
                        if (!r.fromCache && price != null && change != null &&
                            price != f.lastKnownPrice
                        ) {
                            favoriteDao.updateLastKnownPrice(f.ticker, price, change)
                        }
                    }
                    else -> {
                        // Falhou agora: mantém o último valor já mostrado (ou o salvo no banco).
                        map[f.ticker] = previous[f.ticker] ?: FavoriteQuoteUi(
                            ticker = f.ticker,
                            name = f.name,
                            assetType = f.assetType,
                            price = f.lastKnownPrice,
                            changePercent = f.lastKnownChangePercent
                        )
                    }
                }
            }
            _live.value = map
            if (anyFresh) _lastUpdated.value = System.currentTimeMillis()
        } finally {
            refreshMutex.unlock()
        }
    }
}
