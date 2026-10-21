package com.b3insights.app.data.repository

import com.b3insights.app.sync.CloudSync

import com.b3insights.app.data.local.dao.FavoriteAssetDao
import com.b3insights.app.data.local.dao.SearchedAssetDao
import com.b3insights.app.data.local.entity.FavoriteAssetEntity
import com.b3insights.app.data.local.entity.SearchedAssetEntity
import com.b3insights.app.data.model.AssetDetail
import com.b3insights.app.data.model.AssetQuote
import com.b3insights.app.data.model.ChartPeriod
import com.b3insights.app.data.model.DataResult
import com.b3insights.app.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Busca remota (Yahoo padrão + brapi) com cache em memória do último resultado por ticker.
 * Histórico de pesquisas e favoritos ficam no Room (sobrevivem ao fechar o app).
 */
class MarketDataRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val searchedAssetDao: SearchedAssetDao,
    private val favoriteAssetDao: FavoriteAssetDao
) : MarketDataRepository {

    private val lastKnownGood = mutableMapOf<String, AssetDetail>()
    private val lastKnownQuote = mutableMapOf<String, AssetQuote>()
    private val quoteFetchedAt = mutableMapOf<String, Long>()
    private val searchCache = mutableMapOf<String, Pair<Long, List<AssetQuote>>>()

    /** Cotação em RAM por até 45s — evita refazer rede a cada tecla/tela. */
    private val quoteTtlMs = 45_000L
    /** Resultado de busca em RAM por até 3 min. */
    private val searchTtlMs = 180_000L

    /** Limpa caches em RAM (logout / troca de conta) — auditoria C-03. */
    fun clearMemoryCaches() {
        lastKnownGood.clear()
        lastKnownQuote.clear()
        quoteFetchedAt.clear()
        searchCache.clear()
    }

    override suspend fun getAssetDetail(
        ticker: String,
        period: ChartPeriod,
        forceRefresh: Boolean
    ): DataResult<AssetDetail> {
        val tKey = ticker.uppercase()
        // Histórico do gráfico depende do período — cache por ticker+período
        val detailKey = "$tKey|${period.name}"
        if (!forceRefresh) {
            val cached = lastKnownGood[detailKey]
            val age = System.currentTimeMillis() - (quoteFetchedAt[detailKey] ?: 0L)
            if (cached != null && age in 0 until quoteTtlMs) {
                return DataResult.Success(cached, fromCache = false)
            }
        }
        val result = remoteDataSource.fetchAssetDetail(tKey, period)
        return when (result) {
            is DataResult.Success -> {
                lastKnownGood[detailKey] = result.data
                lastKnownQuote[tKey] = result.data.quote
                quoteFetchedAt[detailKey] = System.currentTimeMillis()
                quoteFetchedAt[tKey] = System.currentTimeMillis()
                result
            }
            is DataResult.Error -> {
                val cached = lastKnownGood[detailKey] ?: lastKnownGood.entries
                    .filter { it.key.startsWith("$tKey|") }
                    .maxByOrNull { quoteFetchedAt[it.key] ?: 0L }
                    ?.value
                val offline = result.message == "no_connection" || result.message == "update_error"
                if (cached != null && offline) {
                    DataResult.Success(cached, fromCache = true)
                } else if (cached != null && !offline) {
                    DataResult.Success(cached, fromCache = false)
                } else {
                    result
                }
            }
            DataResult.Loading -> DataResult.Loading
        }
    }

    override suspend fun getQuote(ticker: String): DataResult<AssetQuote> {
        val key = ticker.uppercase()
        val cached = lastKnownQuote[key] ?: lastKnownGood[key]?.quote
        val age = System.currentTimeMillis() - (quoteFetchedAt[key] ?: 0L)
        if (cached != null && age in 0 until quoteTtlMs) {
            return DataResult.Success(cached, fromCache = false)
        }
        val result = remoteDataSource.fetchQuote(key)
        return when (result) {
            is DataResult.Success -> {
                lastKnownQuote[key] = result.data
                quoteFetchedAt[key] = System.currentTimeMillis()
                result
            }
            is DataResult.Error -> {
                val offline = result.message == "no_connection" || result.message == "update_error"
                if (cached != null && offline) {
                    DataResult.Success(cached, fromCache = true)
                } else if (cached != null) {
                    DataResult.Success(cached, fromCache = false)
                } else {
                    result
                }
            }
            DataResult.Loading -> DataResult.Loading
        }
    }

    override suspend fun listAllStocks(limit: Int): DataResult<List<AssetQuote>> =
        remoteDataSource.fetchAllStocks(limit)

    override suspend fun searchAssets(query: String): DataResult<List<AssetQuote>> {
        val key = query.trim().uppercase()
        if (key.isBlank()) return DataResult.Success(emptyList())
        searchCache[key]?.let { (at, data) ->
            if (System.currentTimeMillis() - at < searchTtlMs) {
                return DataResult.Success(data, fromCache = false)
            }
        }
        val result = remoteDataSource.searchTickers(query.trim())
        if (result is DataResult.Success) {
            searchCache[key] = System.currentTimeMillis() to result.data
            // Limita tamanho do cache de busca
            if (searchCache.size > 40) {
                val oldest = searchCache.entries.sortedBy { it.value.first }.take(10)
                oldest.forEach { searchCache.remove(it.key) }
            }
        }
        return result
    }

    override fun getRecentSearches(): Flow<List<String>> =
        searchedAssetDao.getRecentSearches().map { list -> list.map { it.ticker } }

    override suspend fun recordSearch(ticker: String, name: String?, assetType: String?) {
        searchedAssetDao.upsert(
            SearchedAssetEntity(
                ticker = ticker.uppercase(),
                name = name,
                assetType = assetType,
                lastSearchedAt = System.currentTimeMillis()
            )
        )
    }

    override fun getFavorites(): Flow<List<FavoriteAssetEntity>> = favoriteAssetDao.getAllFavorites()

    override fun isFavorite(ticker: String): Flow<Boolean> = favoriteAssetDao.isFavorite(ticker.uppercase())

    override suspend fun toggleFavorite(quote: AssetQuote, currentlyFavorite: Boolean) {
        if (currentlyFavorite) {
            favoriteAssetDao.removeByTicker(quote.ticker.uppercase())
        } else {
            favoriteAssetDao.insert(
                FavoriteAssetEntity(
                    ticker = quote.ticker.uppercase(),
                    name = quote.name,
                    assetType = quote.assetType.name,
                    addedAt = System.currentTimeMillis(),
                    lastKnownPrice = quote.currentPrice,
                    lastKnownChangePercent = quote.dayChangePercent
                )
            )
        }
        CloudSync.pushAfterLocalChange()
    }
}
