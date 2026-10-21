package com.b3insights.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_assets")
data class FavoriteAssetEntity(
    @PrimaryKey val ticker: String,
    val name: String?,
    val assetType: String?,
    val addedAt: Long,
    val lastKnownPrice: Double? = null,
    val lastKnownChangePercent: Double? = null,
    val updatedAt: Long = System.currentTimeMillis()
)
