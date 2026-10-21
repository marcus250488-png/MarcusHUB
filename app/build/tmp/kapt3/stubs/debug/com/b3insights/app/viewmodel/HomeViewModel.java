package com.b3insights.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0011\u001a\u00020\u0012J\u001c\u0010\u0013\u001a\u00020\u00122\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0082@\u00a2\u0006\u0002\u0010\u0015J\u000e\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018J\u0006\u0010\u0019\u001a\u00020\u0012J\b\u0010\u001a\u001a\u00020\u0012H\u0002J\u0006\u0010\u001b\u001a\u00020\u0012R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001d"}, d2 = {"Lcom/b3insights/app/viewmodel/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/b3insights/app/viewmodel/HomeUiState;", "lastPositions", "", "Lcom/b3insights/app/data/local/entity/PortfolioPositionEntity;", "portfolioRepository", "Lcom/b3insights/app/data/repository/PortfolioRepository;", "repository", "Lcom/b3insights/app/data/repository/MarketDataRepositoryImpl;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearResults", "", "loadPortfolio", "positions", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onQueryChange", "query", "", "refresh", "refreshHighlights", "search", "Companion", "app_debug"})
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.repository.MarketDataRepositoryImpl repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.b3insights.app.viewmodel.HomeUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.viewmodel.HomeUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.repository.PortfolioRepository portfolioRepository = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> lastPositions;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> HIGHLIGHT_TICKERS = null;
    @org.jetbrains.annotations.NotNull()
    private static final com.b3insights.app.viewmodel.HomeViewModel.Companion Companion = null;
    
    public HomeViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.viewmodel.HomeUiState> getUiState() {
        return null;
    }
    
    /**
     * Atualiza cotações (chamado ao abrir a Home).
     */
    public final void refresh() {
    }
    
    private final void refreshHighlights() {
    }
    
    private final java.lang.Object loadPortfolio(java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> positions, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void onQueryChange(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void search() {
    }
    
    public final void clearResults() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/b3insights/app/viewmodel/HomeViewModel$Companion;", "", "()V", "HIGHLIGHT_TICKERS", "", "", "getHIGHLIGHT_TICKERS", "()Ljava/util/List;", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getHIGHLIGHT_TICKERS() {
            return null;
        }
    }
}