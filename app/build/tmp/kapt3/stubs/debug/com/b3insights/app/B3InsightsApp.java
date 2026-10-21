package com.b3insights.app;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \"2\u00020\u00012\u00020\u0002:\u0001\"B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020!H\u0016R\u001b\u0010\u0004\u001a\u00020\u00058FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\n\u001a\u00020\u000b8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000e\u0010\t\u001a\u0004\b\f\u0010\rR\u001b\u0010\u000f\u001a\u00020\u00108FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0013\u0010\t\u001a\u0004\b\u0011\u0010\u0012R\u001b\u0010\u0014\u001a\u00020\u00158FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0018\u0010\t\u001a\u0004\b\u0016\u0010\u0017R\u001b\u0010\u0019\u001a\u00020\u001a8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001d\u0010\t\u001a\u0004\b\u001b\u0010\u001c\u00a8\u0006#"}, d2 = {"Lcom/b3insights/app/B3InsightsApp;", "Landroid/app/Application;", "Lcoil/ImageLoaderFactory;", "()V", "alertsRepository", "Lcom/b3insights/app/data/repository/AlertsRepository;", "getAlertsRepository", "()Lcom/b3insights/app/data/repository/AlertsRepository;", "alertsRepository$delegate", "Lkotlin/Lazy;", "database", "Lcom/b3insights/app/data/local/AppDatabase;", "getDatabase", "()Lcom/b3insights/app/data/local/AppDatabase;", "database$delegate", "marketDataRepository", "Lcom/b3insights/app/data/repository/MarketDataRepositoryImpl;", "getMarketDataRepository", "()Lcom/b3insights/app/data/repository/MarketDataRepositoryImpl;", "marketDataRepository$delegate", "portfolioRepository", "Lcom/b3insights/app/data/repository/PortfolioRepository;", "getPortfolioRepository", "()Lcom/b3insights/app/data/repository/PortfolioRepository;", "portfolioRepository$delegate", "remoteDataSource", "Lcom/b3insights/app/data/remote/RemoteDataSource;", "getRemoteDataSource", "()Lcom/b3insights/app/data/remote/RemoteDataSource;", "remoteDataSource$delegate", "newImageLoader", "Lcoil/ImageLoader;", "onCreate", "", "Companion", "app_debug"})
public final class B3InsightsApp extends android.app.Application implements coil.ImageLoaderFactory {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy database$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy remoteDataSource$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy marketDataRepository$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy portfolioRepository$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy alertsRepository$delegate = null;
    private static com.b3insights.app.B3InsightsApp instance;
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.B3InsightsApp.Companion Companion = null;
    
    public B3InsightsApp() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.local.AppDatabase getDatabase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.remote.RemoteDataSource getRemoteDataSource() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.repository.MarketDataRepositoryImpl getMarketDataRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.repository.PortfolioRepository getPortfolioRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.repository.AlertsRepository getAlertsRepository() {
        return null;
    }
    
    /**
     * Loader global do Coil: decodifica SVG (logos da brapi) e guarda os logos em disco.
     */
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public coil.ImageLoader newImageLoader() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086.\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/b3insights/app/B3InsightsApp$Companion;", "", "()V", "<set-?>", "Lcom/b3insights/app/B3InsightsApp;", "instance", "getInstance", "()Lcom/b3insights/app/B3InsightsApp;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.B3InsightsApp getInstance() {
            return null;
        }
    }
}