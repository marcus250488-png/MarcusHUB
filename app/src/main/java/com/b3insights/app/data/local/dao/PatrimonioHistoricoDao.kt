package com.b3insights.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.b3insights.app.data.local.entity.PatrimonioHistoricoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PatrimonioHistoricoDao {
    @Query("SELECT * FROM patrimonio_historico ORDER BY date ASC")
    fun getHistorico(): Flow<List<PatrimonioHistoricoEntity>>

    @Insert
    suspend fun insert(entity: PatrimonioHistoricoEntity)

    @Query("DELETE FROM patrimonio_historico")
    suspend fun deleteAll()

    @Query("SELECT * FROM patrimonio_historico WHERE date = :dayStart LIMIT 1")
    suspend fun getByDay(dayStart: Long): PatrimonioHistoricoEntity?
}
