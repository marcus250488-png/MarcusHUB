package com.b3insights.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "portfolio_positions")
data class PortfolioPositionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val ticker: String,
    val assetType: String,
    val quantity: Double,
    val averagePrice: Double,
    val purchasePrice: Double,
    val purchaseDate: Long,
    val notes: String? = null,
    val tags: String? = null,
    val side: String = "BUY",
    /** Epoch ms — usado no merge local/nuvem (vence o mais recente). */
    val updatedAt: Long = System.currentTimeMillis()
)
