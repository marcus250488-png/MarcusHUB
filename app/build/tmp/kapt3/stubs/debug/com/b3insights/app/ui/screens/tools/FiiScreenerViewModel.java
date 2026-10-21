package com.b3insights.app.ui.screens.tools;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\n0\tJ\u0006\u0010\u001a\u001a\u00020\u0016J\u000e\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u0005J\u001a\u0010\u001d\u001a\u00020\u00162\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u001fR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000e\u00a8\u0006 "}, d2 = {"Lcom/b3insights/app/ui/screens/tools/FiiScreenerViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_filter", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/b3insights/app/ui/screens/tools/FiiFilterState;", "_loading", "", "_rows", "", "Lcom/b3insights/app/ui/screens/tools/FiiRow;", "filter", "Lkotlinx/coroutines/flow/StateFlow;", "getFilter", "()Lkotlinx/coroutines/flow/StateFlow;", "loading", "getLoading", "market", "Lcom/b3insights/app/data/repository/MarketDataRepositoryImpl;", "rows", "getRows", "applyPreset", "", "name", "", "filtered", "refreshQuotes", "setFilter", "newFilter", "updateFilter", "block", "Lkotlin/Function1;", "app_debug"})
public final class FiiScreenerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.repository.MarketDataRepositoryImpl market = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.b3insights.app.ui.screens.tools.FiiRow>> _rows = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.b3insights.app.ui.screens.tools.FiiRow>> rows = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _loading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> loading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.b3insights.app.ui.screens.tools.FiiFilterState> _filter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.ui.screens.tools.FiiFilterState> filter = null;
    
    public FiiScreenerViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.b3insights.app.ui.screens.tools.FiiRow>> getRows() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.ui.screens.tools.FiiFilterState> getFilter() {
        return null;
    }
    
    public final void updateFilter(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.b3insights.app.ui.screens.tools.FiiFilterState, com.b3insights.app.ui.screens.tools.FiiFilterState> block) {
    }
    
    public final void setFilter(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.ui.screens.tools.FiiFilterState newFilter) {
    }
    
    public final void applyPreset(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.ui.screens.tools.FiiRow> filtered() {
        return null;
    }
    
    public final void refreshQuotes() {
    }
}