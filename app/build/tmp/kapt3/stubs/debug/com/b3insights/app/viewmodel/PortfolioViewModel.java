package com.b3insights.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002Jb\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u00142\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00142\b\b\u0002\u0010\u001d\u001a\u00020\u00142\b\b\u0002\u0010\u001e\u001a\u00020\u001f2\b\b\u0002\u0010 \u001a\u00020\u0017J\u001c\u0010!\u001a\u00020\u00122\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0082@\u00a2\u0006\u0002\u0010#J\u000e\u0010$\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u0014J\u0014\u0010%\u001a\u00020\u00122\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\b0\u0007J\u000e\u0010&\u001a\u00020\u00122\u0006\u0010\'\u001a\u00020\bJ\u0006\u0010(\u001a\u00020)J\u0006\u0010*\u001a\u00020\u0012J\\\u0010+\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u00142\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00142\b\b\u0002\u0010\u001d\u001a\u00020\u0014R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006-"}, d2 = {"Lcom/b3insights/app/viewmodel/PortfolioViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/b3insights/app/viewmodel/PortfolioUiState;", "lastPositions", "", "Lcom/b3insights/app/data/local/entity/PortfolioPositionEntity;", "marketDataRepository", "Lcom/b3insights/app/data/repository/MarketDataRepositoryImpl;", "portfolioRepository", "Lcom/b3insights/app/data/repository/PortfolioRepository;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addPosition", "", "ticker", "", "assetType", "quantity", "", "purchasePrice", "purchaseDate", "", "notes", "tags", "side", "syncFinanceiro", "", "valorBrl", "buildUiState", "positions", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "canAddAsset", "deleteAllForTicker", "deletePosition", "entity", "distinctAssetCount", "", "refreshQuotes", "replacePosition", "oldPositions", "app_debug"})
public final class PortfolioViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.repository.PortfolioRepository portfolioRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.repository.MarketDataRepositoryImpl marketDataRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.b3insights.app.viewmodel.PortfolioUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.viewmodel.PortfolioUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> lastPositions;
    
    public PortfolioViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.viewmodel.PortfolioUiState> getUiState() {
        return null;
    }
    
    private final java.lang.Object buildUiState(java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> positions, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void refreshQuotes() {
    }
    
    /**
     * FREE: no máximo FREE_MAX_ASSETS tickers distintos. PRO ilimitado.
     */
    public final boolean canAddAsset(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker) {
        return false;
    }
    
    public final int distinctAssetCount() {
        return 0;
    }
    
    public final void addPosition(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.NotNull()
    java.lang.String assetType, double quantity, double purchasePrice, long purchaseDate, @org.jetbrains.annotations.Nullable()
    java.lang.String notes, @org.jetbrains.annotations.Nullable()
    java.lang.String tags, @org.jetbrains.annotations.NotNull()
    java.lang.String side, boolean syncFinanceiro, double valorBrl) {
    }
    
    public final void deletePosition(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.local.entity.PortfolioPositionEntity entity) {
    }
    
    /**
     * Substitui todas as posições de um ticker por uma linha editada.
     */
    public final void replacePosition(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.NotNull()
    java.lang.String assetType, double quantity, double purchasePrice, long purchaseDate, @org.jetbrains.annotations.Nullable()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> oldPositions, @org.jetbrains.annotations.Nullable()
    java.lang.String tags, @org.jetbrains.annotations.NotNull()
    java.lang.String side) {
    }
    
    public final void deleteAllForTicker(@org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> positions) {
    }
}