package com.b3insights.app.data.repository

import com.b3insights.app.data.model.AssetDetail
import com.b3insights.app.data.model.AssetQuote
import com.b3insights.app.data.model.ChartPeriod
import com.b3insights.app.data.model.DataResult
import com.b3insights.app.data.local.entity.FavoriteAssetEntity
import kotlinx.coroutines.flow.Flow

/**
 * Contrato de acesso a dados de mercado, usado por toda a UI/ViewModels.
 * Mantém a interface separada da implementação (item 20 do escopo) para que a fonte de
 * dados remota possa ser trocada/expandida sem alterar quem consome o repositório.
 */
interface MarketDataRepository {
    suspend fun getAssetDetail(ticker: String, period: ChartPeriod, forceRefresh: Boolean = false): DataResult<AssetDetail>
    suspend fun getQuote(ticker: String): DataResult<AssetQuote>
    suspend fun searchAssets(query: String): DataResult<List<AssetQuote>>
    suspend fun listAllStocks(limit: Int = 500): DataResult<List<AssetQuote>>

    fun getRecentSearches(): Flow<List<String>>
    suspend fun recordSearch(ticker: String, name: String?, assetType: String?)

    fun getFavorites(): Flow<List<FavoriteAssetEntity>>
    fun isFavorite(ticker: String): Flow<Boolean>
    suspend fun toggleFavorite(quote: AssetQuote, currentlyFavorite: Boolean)
}
