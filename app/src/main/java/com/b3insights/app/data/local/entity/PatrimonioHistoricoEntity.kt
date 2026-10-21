package com.b3insights.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Snapshot periódico do valor total da carteira, usado para o gráfico de evolução patrimonial. */
@Entity(tableName = "patrimonio_historico")
data class PatrimonioHistoricoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: Long,
    val totalValue: Double,
    val totalInvested: Double
)
