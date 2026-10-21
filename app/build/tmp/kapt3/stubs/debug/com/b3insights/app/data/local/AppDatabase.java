package com.b3insights.app.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&\u00a8\u0006\u0010"}, d2 = {"Lcom/b3insights/app/data/local/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "favoriteAssetDao", "Lcom/b3insights/app/data/local/dao/FavoriteAssetDao;", "patrimonioHistoricoDao", "Lcom/b3insights/app/data/local/dao/PatrimonioHistoricoDao;", "portfolioDao", "Lcom/b3insights/app/data/local/dao/PortfolioDao;", "priceAlertDao", "Lcom/b3insights/app/data/local/dao/PriceAlertDao;", "searchedAssetDao", "Lcom/b3insights/app/data/local/dao/SearchedAssetDao;", "userSettingsDao", "Lcom/b3insights/app/data/local/dao/UserSettingsDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.b3insights.app.data.local.entity.SearchedAssetEntity.class, com.b3insights.app.data.local.entity.FavoriteAssetEntity.class, com.b3insights.app.data.local.entity.PortfolioPositionEntity.class, com.b3insights.app.data.local.entity.PatrimonioHistoricoEntity.class, com.b3insights.app.data.local.entity.UserSettingsEntity.class, com.b3insights.app.data.local.entity.PriceAlertEntity.class}, version = 7, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.b3insights.app.data.local.AppDatabase INSTANCE;
    
    /**
     * 5 → 6: colunas updatedAt + allowEmptyCloudRestore (auditoria C-08 / C-04).
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.room.migration.Migration MIGRATION_6_7 = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.room.migration.Migration MIGRATION_5_6 = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.data.local.AppDatabase.Companion Companion = null;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.b3insights.app.data.local.dao.SearchedAssetDao searchedAssetDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.b3insights.app.data.local.dao.FavoriteAssetDao favoriteAssetDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.b3insights.app.data.local.dao.PortfolioDao portfolioDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.b3insights.app.data.local.dao.PatrimonioHistoricoDao patrimonioHistoricoDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.b3insights.app.data.local.dao.UserSettingsDao userSettingsDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.b3insights.app.data.local.dao.PriceAlertDao priceAlertDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b\u00a8\u0006\u000e"}, d2 = {"Lcom/b3insights/app/data/local/AppDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/b3insights/app/data/local/AppDatabase;", "MIGRATION_5_6", "Landroidx/room/migration/Migration;", "getMIGRATION_5_6", "()Landroidx/room/migration/Migration;", "MIGRATION_6_7", "getMIGRATION_6_7", "getInstance", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * 5 → 6: colunas updatedAt + allowEmptyCloudRestore (auditoria C-08 / C-04).
         */
        @org.jetbrains.annotations.NotNull()
        public final androidx.room.migration.Migration getMIGRATION_6_7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.room.migration.Migration getMIGRATION_5_6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.data.local.AppDatabase getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}