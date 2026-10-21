package com.b3insights.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searched_assets")
data class SearchedAssetEntity(
    @PrimaryKey val ticker: String,
    val name: String?,
    val assetType: String?,
    val lastSearchedAt: Long,
    val updatedAt: Long = System.currentTimeMillis()
)
