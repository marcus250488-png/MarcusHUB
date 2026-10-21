package com.b3insights.app.data.local.dao

import androidx.room.*
import com.b3insights.app.data.local.entity.PortfolioPositionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PortfolioDao {
    @Query("SELECT * FROM portfolio_positions ORDER BY purchaseDate DESC")
    fun getAllPositions(): Flow<List<PortfolioPositionEntity>>

    @Query("SELECT * FROM portfolio_positions WHERE ticker = :ticker ORDER BY purchaseDate DESC")
    fun getPositionsForTicker(ticker: String): Flow<List<PortfolioPositionEntity>>

    @Insert
    suspend fun insert(entity: PortfolioPositionEntity): Long

    @Update
    suspend fun update(entity: PortfolioPositionEntity)

    @Delete
    suspend fun delete(entity: PortfolioPositionEntity)

    @Query("SELECT DISTINCT ticker FROM portfolio_positions")
    suspend fun getDistinctTickers(): List<String>

    @Query("SELECT * FROM portfolio_positions")
    suspend fun getAllOnce(): List<PortfolioPositionEntity>

    @Query("DELETE FROM portfolio_positions")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<PortfolioPositionEntity>)
}
