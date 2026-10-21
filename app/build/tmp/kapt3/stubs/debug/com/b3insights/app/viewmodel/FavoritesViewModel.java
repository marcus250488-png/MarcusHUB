package com.b3insights.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u001c\u001a\u00020\u001dH\u0086@\u00a2\u0006\u0002\u0010\u001eR\u0016\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0006\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0019\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R#\u0010\u0016\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u00070\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/b3insights/app/viewmodel/FavoritesViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_lastUpdated", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_live", "", "", "Lcom/b3insights/app/viewmodel/FavoriteQuoteUi;", "app", "Lcom/b3insights/app/B3InsightsApp;", "favoriteDao", "Lcom/b3insights/app/data/local/dao/FavoriteAssetDao;", "favorites", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/b3insights/app/data/local/entity/FavoriteAssetEntity;", "getFavorites", "()Lkotlinx/coroutines/flow/StateFlow;", "lastUpdated", "getLastUpdated", "liveQuotes", "getLiveQuotes", "refreshMutex", "Lkotlinx/coroutines/sync/Mutex;", "repository", "Lcom/b3insights/app/data/repository/MarketDataRepositoryImpl;", "refreshQuotes", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class FavoritesViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.B3InsightsApp app = null;
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.repository.MarketDataRepositoryImpl repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.local.dao.FavoriteAssetDao favoriteDao = null;
    
    /**
     * Evita duas atualizações ao mesmo tempo (ex.: abrir a tela e o timer de 30s coincidirem).
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.sync.Mutex refreshMutex = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.b3insights.app.data.local.entity.FavoriteAssetEntity>> favorites = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Map<java.lang.String, com.b3insights.app.viewmodel.FavoriteQuoteUi>> _live = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.b3insights.app.viewmodel.FavoriteQuoteUi>> liveQuotes = null;
    
    /**
     * Horário da última atualização que realmente veio da rede (null = ainda não atualizou).
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> _lastUpdated = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> lastUpdated = null;
    
    public FavoritesViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.b3insights.app.data.local.entity.FavoriteAssetEntity>> getFavorites() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.b3insights.app.viewmodel.FavoriteQuoteUi>> getLiveQuotes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Long> getLastUpdated() {
        return null;
    }
    
    /**
     * Busca a cotação ATUAL de cada favorito (em paralelo) e guarda o resultado também no Room,
     * para o app mostrar o último preço real quando estiver sem internet.
     * A tela chama isto ao abrir e a cada 30s enquanto estiver visível.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object refreshQuotes(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}