package com.b3insights.app

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.b3insights.app.data.local.AppDatabase
import com.b3insights.app.data.remote.RemoteDataSource
import com.b3insights.app.data.repository.AlertsRepository
import com.b3insights.app.data.repository.MarketDataRepositoryImpl
import com.b3insights.app.data.repository.PortfolioRepository

class B3InsightsApp : Application(), ImageLoaderFactory {

    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }

    val remoteDataSource: RemoteDataSource by lazy { RemoteDataSource() }

    val marketDataRepository: MarketDataRepositoryImpl by lazy {
        MarketDataRepositoryImpl(
            remoteDataSource = remoteDataSource,
            searchedAssetDao = database.searchedAssetDao(),
            favoriteAssetDao = database.favoriteAssetDao()
        )
    }

    val portfolioRepository: PortfolioRepository by lazy {
        PortfolioRepository(database.portfolioDao(), database.patrimonioHistoricoDao())
    }

    val alertsRepository: AlertsRepository by lazy {
        AlertsRepository(database.priceAlertDao())
    }

    /** Loader global do Coil: decodifica SVG (logos da brapi) e guarda os logos em disco. */
    override fun newImageLoader(): ImageLoader =
        ImageLoader.Builder(this)
            .components { add(SvgDecoder.Factory()) }
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.20)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("logo_cache"))
                    .maxSizeBytes(50L * 1024 * 1024)
                    .build()
            }
            .respectCacheHeaders(false)
            .build()

    override fun onCreate() {
        super.onCreate()
        instance = this
        com.b3insights.app.notifications.ValuationMonitor.schedule(this)
        // Se já estiver logado (sessão Google), tenta restaurar/enviar dados
        Thread {
            try {
                val user = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    kotlinx.coroutines.runBlocking {
                        com.b3insights.app.sync.CloudSync.syncOnLogin()
                    }
                }
            } catch (_: Exception) {
            }
        }.start()
    }

    companion object {
        lateinit var instance: B3InsightsApp
            private set
    }
}
