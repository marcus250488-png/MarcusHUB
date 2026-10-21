package com.b3insights.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.b3insights.app.data.local.dao.FavoriteAssetDao
import com.b3insights.app.data.local.dao.PatrimonioHistoricoDao
import com.b3insights.app.data.local.dao.PortfolioDao
import com.b3insights.app.data.local.dao.PriceAlertDao
import com.b3insights.app.data.local.dao.SearchedAssetDao
import com.b3insights.app.data.local.dao.UserSettingsDao
import com.b3insights.app.data.local.entity.FavoriteAssetEntity
import com.b3insights.app.data.local.entity.PatrimonioHistoricoEntity
import com.b3insights.app.data.local.entity.PortfolioPositionEntity
import com.b3insights.app.data.local.entity.PriceAlertEntity
import com.b3insights.app.data.local.entity.SearchedAssetEntity
import com.b3insights.app.data.local.entity.UserSettingsEntity

@Database(
    entities = [
        SearchedAssetEntity::class,
        FavoriteAssetEntity::class,
        PortfolioPositionEntity::class,
        PatrimonioHistoricoEntity::class,
        UserSettingsEntity::class,
        PriceAlertEntity::class
    ],
    version = 7,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun searchedAssetDao(): SearchedAssetDao
    abstract fun favoriteAssetDao(): FavoriteAssetDao
    abstract fun portfolioDao(): PortfolioDao
    abstract fun patrimonioHistoricoDao(): PatrimonioHistoricoDao
    abstract fun userSettingsDao(): UserSettingsDao
    abstract fun priceAlertDao(): PriceAlertDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /** 5 → 6: colunas updatedAt + allowEmptyCloudRestore (auditoria C-08 / C-04). */
        val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE user_settings ADD COLUMN targetRendaMensal REAL NOT NULL DEFAULT 0.0")
            }
        }

        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE portfolio_positions ADD COLUMN updatedAt INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE favorite_assets ADD COLUMN updatedAt INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE price_alerts ADD COLUMN updatedAt INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE searched_assets ADD COLUMN updatedAt INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE user_settings ADD COLUMN allowEmptyCloudRestore INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "b3insights.db"
                )
                    .addMigrations(MIGRATION_5_6, MIGRATION_6_7)
                    // Só versões muito antigas (pré-v5) sem cadeia de migração
                    .fallbackToDestructiveMigrationFrom(1, 2, 3, 4)
                    .build().also { INSTANCE = it }
            }
        }
    }
}
