package com.b3insights.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0002J\u000e\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u000e\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\rJ\u0006\u0010\u0018\u001a\u00020\rR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0019"}, d2 = {"Lcom/b3insights/app/viewmodel/AssetDetailViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/b3insights/app/viewmodel/AssetDetailUiState;", "repository", "Lcom/b3insights/app/data/repository/MarketDataRepositoryImpl;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "fetch", "", "forceRefresh", "", "load", "ticker", "", "observeFavorite", "onPeriodSelected", "period", "Lcom/b3insights/app/data/model/ChartPeriod;", "retry", "toggleFavorite", "app_debug"})
public final class AssetDetailViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.repository.MarketDataRepositoryImpl repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.b3insights.app.viewmodel.AssetDetailUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.viewmodel.AssetDetailUiState> uiState = null;
    
    public AssetDetailViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.viewmodel.AssetDetailUiState> getUiState() {
        return null;
    }
    
    public final void load(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker) {
    }
    
    private final void observeFavorite(java.lang.String ticker) {
    }
    
    public final void onPeriodSelected(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.model.ChartPeriod period) {
    }
    
    public final void retry() {
    }
    
    private final void fetch(boolean forceRefresh) {
    }
    
    public final void toggleFavorite() {
    }
}