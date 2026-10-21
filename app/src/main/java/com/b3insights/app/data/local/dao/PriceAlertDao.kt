package com.b3insights.app.data.local.dao

import androidx.room.*
import com.b3insights.app.data.local.entity.PriceAlertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PriceAlertDao {
    @Query("SELECT * FROM price_alerts ORDER BY createdAt DESC")
    fun getAll(): Flow<List<PriceAlertEntity>>

    @Query("SELECT * FROM price_alerts WHERE enabled = 1")
    suspend fun getEnabled(): List<PriceAlertEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PriceAlertEntity): Long

    @Update
    suspend fun update(entity: PriceAlertEntity)

    @Delete
    suspend fun delete(entity: PriceAlertEntity)

    @Query("SELECT * FROM price_alerts")
    suspend fun getAllOnce(): List<PriceAlertEntity>

    @Query("DELETE FROM price_alerts")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<PriceAlertEntity>)
}
