package com.b3insights.app.ui.screens.tools;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u001bJ\u001a\u0010\u001c\u001a\u00020\u00172\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00170\u001eJ\u000e\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u000bJ\u000e\u0010!\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u000bJ\u0006\u0010\"\u001a\u00020\u0017R \u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR#\u0010\u000e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/b3insights/app/ui/screens/tools/AlertsViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_livePrices", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "", "", "alerts", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/b3insights/app/data/local/entity/PriceAlertEntity;", "getAlerts", "()Lkotlinx/coroutines/flow/Flow;", "livePrices", "Lkotlinx/coroutines/flow/StateFlow;", "getLivePrices", "()Lkotlinx/coroutines/flow/StateFlow;", "market", "Lcom/b3insights/app/data/repository/MarketDataRepositoryImpl;", "repo", "Lcom/b3insights/app/data/repository/AlertsRepository;", "add", "", "ticker", "target", "above", "", "checkNow", "onResult", "Lkotlin/Function1;", "delete", "a", "reenable", "refreshPrices", "app_debug"})
public final class AlertsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.repository.AlertsRepository repo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.repository.MarketDataRepositoryImpl market = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.b3insights.app.data.local.entity.PriceAlertEntity>> alerts = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Map<java.lang.String, java.lang.Double>> _livePrices = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, java.lang.Double>> livePrices = null;
    
    public AlertsViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.b3insights.app.data.local.entity.PriceAlertEntity>> getAlerts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, java.lang.Double>> getLivePrices() {
        return null;
    }
    
    public final void refreshPrices() {
    }
    
    public final void add(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, double target, boolean above) {
    }
    
    public final void delete(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.local.entity.PriceAlertEntity a) {
    }
    
    public final void reenable(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.local.entity.PriceAlertEntity a) {
    }
    
    public final void checkNow(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onResult) {
    }
}