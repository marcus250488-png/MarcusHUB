package com.b3insights.app.data.repository;

/**
 * Contrato de acesso a dados de mercado, usado por toda a UI/ViewModels.
 * Mantém a interface separada da implementação (item 20 do escopo) para que a fonte de
 * dados remota possa ser trocada/expandida sem alterar quem consome o repositório.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\n\bf\u0018\u00002\u00020\u0001J.\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u00a6@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\rH&J\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u00a6@\u00a2\u0006\u0002\u0010\u0012J\u0014\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u000e0\rH&J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\n0\r2\u0006\u0010\u0005\u001a\u00020\u0006H&J$\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u000e0\u00032\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u00a6@\u00a2\u0006\u0002\u0010\u0018J*\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u001b\u001a\u0004\u0018\u00010\u00062\b\u0010\u001c\u001a\u0004\u0018\u00010\u0006H\u00a6@\u00a2\u0006\u0002\u0010\u001dJ\"\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u000e0\u00032\u0006\u0010\u001f\u001a\u00020\u0006H\u00a6@\u00a2\u0006\u0002\u0010\u0012J\u001e\u0010 \u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\nH\u00a6@\u00a2\u0006\u0002\u0010#\u00a8\u0006$"}, d2 = {"Lcom/b3insights/app/data/repository/MarketDataRepository;", "", "getAssetDetail", "Lcom/b3insights/app/data/model/DataResult;", "Lcom/b3insights/app/data/model/AssetDetail;", "ticker", "", "period", "Lcom/b3insights/app/data/model/ChartPeriod;", "forceRefresh", "", "(Ljava/lang/String;Lcom/b3insights/app/data/model/ChartPeriod;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFavorites", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/b3insights/app/data/local/entity/FavoriteAssetEntity;", "getQuote", "Lcom/b3insights/app/data/model/AssetQuote;", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRecentSearches", "isFavorite", "listAllStocks", "limit", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recordSearch", "", "name", "assetType", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchAssets", "query", "toggleFavorite", "quote", "currentlyFavorite", "(Lcom/b3insights/app/data/model/AssetQuote;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface MarketDataRepository {
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAssetDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.model.ChartPeriod period, boolean forceRefresh, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.model.DataResult<com.b3insights.app.data.model.AssetDetail>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuote(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.model.DataResult<com.b3insights.app.data.model.AssetQuote>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchAssets(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.model.DataResult<? extends java.util.List<com.b3insights.app.data.model.AssetQuote>>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object listAllStocks(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.model.DataResult<? extends java.util.List<com.b3insights.app.data.model.AssetQuote>>> $completion);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getRecentSearches();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object recordSearch(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String assetType, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.b3insights.app.data.local.entity.FavoriteAssetEntity>> getFavorites();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Boolean> isFavorite(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object toggleFavorite(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.model.AssetQuote quote, boolean currentlyFavorite, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Contrato de acesso a dados de mercado, usado por toda a UI/ViewModels.
     * Mantém a interface separada da implementação (item 20 do escopo) para que a fonte de
     * dados remota possa ser trocada/expandida sem alterar quem consome o repositório.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}