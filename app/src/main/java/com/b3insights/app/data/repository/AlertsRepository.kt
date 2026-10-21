package com.b3insights.app.data.repository

import com.b3insights.app.data.local.dao.PriceAlertDao
import com.b3insights.app.sync.CloudSync
import com.b3insights.app.data.local.entity.PriceAlertEntity
import kotlinx.coroutines.flow.Flow

class AlertsRepository(private val dao: PriceAlertDao) {
    fun getAll(): Flow<List<PriceAlertEntity>> = dao.getAll()
    suspend fun getEnabled(): List<PriceAlertEntity> = dao.getEnabled()
    suspend fun add(ticker: String, target: Double, above: Boolean) {
        dao.insert(
            PriceAlertEntity(
                ticker = ticker.uppercase(),
                targetPrice = target,
                above = above
            )
        )
        CloudSync.pushAfterLocalChange()
    }
    suspend fun update(entity: PriceAlertEntity) {
        dao.update(entity)
        CloudSync.pushAfterLocalChange()
    }
    suspend fun delete(entity: PriceAlertEntity) {
        dao.delete(entity)
        CloudSync.pushAfterLocalChange()
    }
}
