package com.b3insights.app.data.local.dao

import androidx.room.*
import com.b3insights.app.data.local.entity.SearchedAssetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchedAssetDao {
    @Query("SELECT * FROM searched_assets ORDER BY lastSearchedAt DESC LIMIT 3")
    fun getRecentSearches(): Flow<List<SearchedAssetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: SearchedAssetEntity)

    @Query("DELETE FROM searched_assets")
    suspend fun clearAll()

    @Query("SELECT * FROM searched_assets ORDER BY lastSearchedAt DESC LIMIT 20")
    suspend fun getAllOnce(): List<SearchedAssetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<SearchedAssetEntity>)
}
