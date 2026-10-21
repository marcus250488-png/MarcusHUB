package com.b3insights.app.ui.screens.tools;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0018\u001a\u00020\u0019J\u0016\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u001cH\u0082@\u00a2\u0006\u0002\u0010\u001dJ\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000b0\nJ\u0006\u0010\u001f\u001a\u00020\u0019J\u0006\u0010 \u001a\u00020\u0019J\u0006\u0010!\u001a\u00020\u0019J\u0006\u0010\"\u001a\u00020\u0019J\u001a\u0010#\u001a\u00020\u00192\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050%R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000f\u00a8\u0006&"}, d2 = {"Lcom/b3insights/app/ui/screens/tools/StockValuationViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_draft", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/b3insights/app/ui/screens/tools/StockValFilter;", "_filter", "_loading", "", "_rows", "", "Lcom/b3insights/app/ui/screens/tools/StockValRow;", "draft", "Lkotlinx/coroutines/flow/StateFlow;", "getDraft", "()Lkotlinx/coroutines/flow/StateFlow;", "filter", "getFilter", "loading", "getLoading", "market", "Lcom/b3insights/app/data/repository/MarketDataRepositoryImpl;", "rows", "getRows", "applyDraft", "", "computeRow", "entry", "Lcom/b3insights/app/data/model/StockCatalogEntry;", "(Lcom/b3insights/app/data/model/StockCatalogEntry;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "filtered", "loadBatch", "loadForCurrentFilter", "openDraftFromCurrent", "resetDraft", "updateDraft", "block", "Lkotlin/Function1;", "app_debug"})
public final class StockValuationViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.repository.MarketDataRepositoryImpl market = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.b3insights.app.ui.screens.tools.StockValRow>> _rows = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.b3insights.app.ui.screens.tools.StockValRow>> rows = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _loading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> loading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.b3insights.app.ui.screens.tools.StockValFilter> _filter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.ui.screens.tools.StockValFilter> filter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.b3insights.app.ui.screens.tools.StockValFilter> _draft = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.ui.screens.tools.StockValFilter> draft = null;
    
    public StockValuationViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.b3insights.app.ui.screens.tools.StockValRow>> getRows() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.ui.screens.tools.StockValFilter> getFilter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.ui.screens.tools.StockValFilter> getDraft() {
        return null;
    }
    
    public final void updateDraft(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.b3insights.app.ui.screens.tools.StockValFilter, com.b3insights.app.ui.screens.tools.StockValFilter> block) {
    }
    
    public final void applyDraft() {
    }
    
    public final void resetDraft() {
    }
    
    public final void openDraftFromCurrent() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.ui.screens.tools.StockValRow> filtered() {
        return null;
    }
    
    /**
     * Não dispara 100+ requests ao abrir.
     * Só carrega valuation do setor (ou da busca) quando o usuário aplica o filtro.
     */
    public final void loadBatch() {
    }
    
    /**
     * Carrega cotação + fundamentos só dos tickers visíveis (setor/busca).
     */
    public final void loadForCurrentFilter() {
    }
    
    private final java.lang.Object computeRow(com.b3insights.app.data.model.StockCatalogEntry entry, kotlin.coroutines.Continuation<? super com.b3insights.app.ui.screens.tools.StockValRow> $completion) {
        return null;
    }
}