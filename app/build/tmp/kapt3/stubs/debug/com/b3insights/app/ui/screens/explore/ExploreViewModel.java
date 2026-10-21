package com.b3insights.app.ui.screens.explore;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\"\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\rH\u0082@\u00a2\u0006\u0002\u0010\u0011J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u0013H\u0082@\u00a2\u0006\u0002\u0010\u0017J\u001c\u0010\u0018\u001a\u00020\u00132\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\rH\u0082@\u00a2\u0006\u0002\u0010\u0011R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0019"}, d2 = {"Lcom/b3insights/app/ui/screens/explore/ExploreViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_ui", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/b3insights/app/ui/screens/explore/ExploreUiState;", "repo", "Lcom/b3insights/app/data/repository/MarketDataRepositoryImpl;", "ui", "Lkotlinx/coroutines/flow/StateFlow;", "getUi", "()Lkotlinx/coroutines/flow/StateFlow;", "fetchQuotes", "", "Lcom/b3insights/app/data/model/AssetQuote;", "tickers", "", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "load", "", "category", "Lcom/b3insights/app/ui/screens/explore/ExploreCategory;", "loadAllStocks", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadTickers", "app_debug"})
public final class ExploreViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.repository.MarketDataRepositoryImpl repo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.b3insights.app.ui.screens.explore.ExploreUiState> _ui = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.ui.screens.explore.ExploreUiState> ui = null;
    
    public ExploreViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.ui.screens.explore.ExploreUiState> getUi() {
        return null;
    }
    
    public final void load(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.ui.screens.explore.ExploreCategory category) {
    }
    
    private final java.lang.Object loadAllStocks(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object loadTickers(java.util.List<java.lang.String> tickers, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object fetchQuotes(java.util.List<java.lang.String> tickers, kotlin.coroutines.Continuation<? super java.util.List<com.b3insights.app.data.model.AssetQuote>> $completion) {
        return null;
    }
}