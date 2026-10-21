package com.b3insights.app.ui.screens.dividends;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0015"}, d2 = {"Lcom/b3insights/app/ui/screens/dividends/DividendAgendaViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_ui", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/b3insights/app/ui/screens/dividends/AgendaUiState;", "portfolioRepo", "Lcom/b3insights/app/data/repository/PortfolioRepository;", "repository", "Lcom/b3insights/app/data/repository/MarketDataRepositoryImpl;", "ui", "Lkotlinx/coroutines/flow/StateFlow;", "getUi", "()Lkotlinx/coroutines/flow/StateFlow;", "load", "", "category", "Lcom/b3insights/app/ui/screens/dividends/DividendCategory;", "year", "", "month", "app_debug"})
public final class DividendAgendaViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.repository.MarketDataRepositoryImpl repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.repository.PortfolioRepository portfolioRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.b3insights.app.ui.screens.dividends.AgendaUiState> _ui = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.ui.screens.dividends.AgendaUiState> ui = null;
    
    public DividendAgendaViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.ui.screens.dividends.AgendaUiState> getUi() {
        return null;
    }
    
    public final void load(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.ui.screens.dividends.DividendCategory category, int year, int month) {
    }
}