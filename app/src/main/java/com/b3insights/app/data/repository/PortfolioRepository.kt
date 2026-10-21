package com.b3insights.app.data.repository

import com.b3insights.app.data.local.dao.PatrimonioHistoricoDao
import com.b3insights.app.data.local.dao.PortfolioDao
import com.b3insights.app.data.local.entity.PatrimonioHistoricoEntity
import com.b3insights.app.data.local.entity.PortfolioPositionEntity
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.sync.CloudSync
import com.b3insights.app.sync.FinanceiroHub
import kotlinx.coroutines.flow.Flow

class PortfolioRepository(
    private val portfolioDao: PortfolioDao,
    private val patrimonioHistoricoDao: PatrimonioHistoricoDao
) {
    fun getAllPositions(): Flow<List<PortfolioPositionEntity>> = portfolioDao.getAllPositions()

    fun getPositionsForTicker(ticker: String): Flow<List<PortfolioPositionEntity>> =
        portfolioDao.getPositionsForTicker(ticker.uppercase())

    suspend fun addPosition(
        ticker: String,
        assetType: String,
        quantity: Double,
        averagePrice: Double,
        purchasePrice: Double,
        purchaseDate: Long,
        notes: String?,
        tags: String? = null,
        side: String = "BUY",
        /** Se true, publica o aporte no hub para o Financeiro mover o dinheiro (Nubank → Inter). */
        syncFinanceiro: Boolean = false,
        /** Valor total da operação em R$ (já convertido se for cripto em US$). */
        valorBrl: Double = 0.0
    ) {
        portfolioDao.insert(
            PortfolioPositionEntity(
                ticker = ticker.uppercase(),
                assetType = assetType,
                quantity = quantity,
                averagePrice = averagePrice,
                purchasePrice = purchasePrice,
                purchaseDate = purchaseDate,
                notes = notes,
                tags = tags,
                side = side
            )
        )
        if (syncFinanceiro && valorBrl > 0.0) {
            FinanceiroHub.publicar(
                B3InsightsApp.instance,
                FinanceiroHub.Aporte(
                    ticker = ticker.uppercase(),
                    assetType = assetType,
                    side = side,
                    quantity = quantity,
                    price = purchasePrice,
                    valorBrl = valorBrl,
                    dataMillis = purchaseDate,
                    tags = tags,
                    notes = notes
                )
            )
        }
        CloudSync.pushAfterLocalChange()
    }

    suspend fun updatePosition(entity: PortfolioPositionEntity) {
        portfolioDao.update(entity)
        CloudSync.pushAfterLocalChange()
    }

    suspend fun deletePosition(entity: PortfolioPositionEntity) {
        portfolioDao.delete(entity)
        CloudSync.pushAfterLocalChange()
    }

    fun getPatrimonioHistorico(): Flow<List<PatrimonioHistoricoEntity>> =
        patrimonioHistoricoDao.getHistorico()

    suspend fun recordPatrimonioSnapshot(totalValue: Double, totalInvested: Double) {
        patrimonioHistoricoDao.insert(
            PatrimonioHistoricoEntity(
                date = System.currentTimeMillis(),
                totalValue = totalValue,
                totalInvested = totalInvested
            )
        )
    }
}
