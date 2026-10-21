package com.b3insights.app.data.local.dao

import androidx.room.*
import com.b3insights.app.data.local.entity.FavoriteAssetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAssetDao {
    @Query("SELECT * FROM favorite_assets ORDER BY addedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteAssetEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_assets WHERE ticker = :ticker)")
    fun isFavorite(ticker: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FavoriteAssetEntity)

    @Query("DELETE FROM favorite_assets WHERE ticker = :ticker")
    suspend fun removeByTicker(ticker: String)

    @Query("UPDATE favorite_assets SET lastKnownPrice = :price, lastKnownChangePercent = :changePercent WHERE ticker = :ticker")
    suspend fun updateLastKnownPrice(ticker: String, price: Double, changePercent: Double)

    @Query("SELECT * FROM favorite_assets")
    suspend fun getAllOnce(): List<FavoriteAssetEntity>

    @Query("DELETE FROM favorite_assets")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<FavoriteAssetEntity>)
}
