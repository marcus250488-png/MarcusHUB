package com.b3insights.app.data.repository;

/**
 * Busca remota (Yahoo padrão + brapi) com cache em memória do último resultado por ticker.
 * Histórico de pesquisas e favoritos ficam no Room (sobrevivem ao fechar o app).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\f\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u0016\u001a\u00020\u0017J,\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\f0\u00192\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0096@\u00a2\u0006\u0002\u0010\u001fJ\u0014\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\u00140!H\u0016J\u001c\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00192\u0006\u0010\u001a\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010$J\u0014\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00140!H\u0016J\u0016\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001e0!2\u0006\u0010\u001a\u001a\u00020\u000bH\u0016J\"\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00140\u00192\u0006\u0010(\u001a\u00020)H\u0096@\u00a2\u0006\u0002\u0010*J*\u0010+\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u000b2\b\u0010,\u001a\u0004\u0018\u00010\u000b2\b\u0010-\u001a\u0004\u0018\u00010\u000bH\u0096@\u00a2\u0006\u0002\u0010.J\"\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00140\u00192\u0006\u00100\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010$J\u001e\u00101\u001a\u00020\u00172\u0006\u00102\u001a\u00020\u000e2\u0006\u00103\u001a\u00020\u001eH\u0096@\u00a2\u0006\u0002\u00104R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000e0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00100\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R,\u0010\u0012\u001a \u0012\u0004\u0012\u00020\u000b\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00140\u00130\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0010X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00065"}, d2 = {"Lcom/b3insights/app/data/repository/MarketDataRepositoryImpl;", "Lcom/b3insights/app/data/repository/MarketDataRepository;", "remoteDataSource", "Lcom/b3insights/app/data/remote/RemoteDataSource;", "searchedAssetDao", "Lcom/b3insights/app/data/local/dao/SearchedAssetDao;", "favoriteAssetDao", "Lcom/b3insights/app/data/local/dao/FavoriteAssetDao;", "(Lcom/b3insights/app/data/remote/RemoteDataSource;Lcom/b3insights/app/data/local/dao/SearchedAssetDao;Lcom/b3insights/app/data/local/dao/FavoriteAssetDao;)V", "lastKnownGood", "", "", "Lcom/b3insights/app/data/model/AssetDetail;", "lastKnownQuote", "Lcom/b3insights/app/data/model/AssetQuote;", "quoteFetchedAt", "", "quoteTtlMs", "searchCache", "Lkotlin/Pair;", "", "searchTtlMs", "clearMemoryCaches", "", "getAssetDetail", "Lcom/b3insights/app/data/model/DataResult;", "ticker", "period", "Lcom/b3insights/app/data/model/ChartPeriod;", "forceRefresh", "", "(Ljava/lang/String;Lcom/b3insights/app/data/model/ChartPeriod;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFavorites", "Lkotlinx/coroutines/flow/Flow;", "Lcom/b3insights/app/data/local/entity/FavoriteAssetEntity;", "getQuote", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRecentSearches", "isFavorite", "listAllStocks", "limit", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recordSearch", "name", "assetType", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchAssets", "query", "toggleFavorite", "quote", "currentlyFavorite", "(Lcom/b3insights/app/data/model/AssetQuote;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class MarketDataRepositoryImpl implements com.b3insights.app.data.repository.MarketDataRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.remote.RemoteDataSource remoteDataSource = null;
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.local.dao.SearchedAssetDao searchedAssetDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.local.dao.FavoriteAssetDao favoriteAssetDao = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, com.b3insights.app.data.model.AssetDetail> lastKnownGood = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, com.b3insights.app.data.model.AssetQuote> lastKnownQuote = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Long> quoteFetchedAt = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, kotlin.Pair<java.lang.Long, java.util.List<com.b3insights.app.data.model.AssetQuote>>> searchCache = null;
    
    /**
     * Cotação em RAM por até 45s — evita refazer rede a cada tecla/tela.
     */
    private final long quoteTtlMs = 45000L;
    
    /**
     * Resultado de busca em RAM por até 3 min.
     */
    private final long searchTtlMs = 180000L;
    
    public MarketDataRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.remote.RemoteDataSource remoteDataSource, @org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.local.dao.SearchedAssetDao searchedAssetDao, @org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.local.dao.FavoriteAssetDao favoriteAssetDao) {
        super();
    }
    
    /**
     * Limpa caches em RAM (logout / troca de conta) — auditoria C-03.
     */
    public final void clearMemoryCaches() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getAssetDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.model.ChartPeriod period, boolean forceRefresh, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.model.DataResult<com.b3insights.app.data.model.AssetDetail>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getQuote(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.model.DataResult<com.b3insights.app.data.model.AssetQuote>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object listAllStocks(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.model.DataResult<? extends java.util.List<com.b3insights.app.data.model.AssetQuote>>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object searchAssets(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.model.DataResult<? extends java.util.List<com.b3insights.app.data.model.AssetQuote>>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getRecentSearches() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object recordSearch(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String assetType, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.b3insights.app.data.local.entity.FavoriteAssetEntity>> getFavorites() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.Boolean> isFavorite(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object toggleFavorite(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.model.AssetQuote quote, boolean currentlyFavorite, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}