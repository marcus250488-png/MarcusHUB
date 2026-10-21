package com.b3insights.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0019\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BU\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\bH\u00c6\u0003J\u0010\u0010\u001f\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\rH\u00c6\u0003J^\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\u000b\u001a\u00020\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rH\u00c6\u0001\u00a2\u0006\u0002\u0010#J\u0013\u0010$\u001a\u00020\u00032\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010&\u001a\u00020\'H\u00d6\u0001J\t\u0010(\u001a\u00020\rH\u00d6\u0001R\u0013\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0012R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0015\u0010\n\u001a\u0004\u0018\u00010\b\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019\u00a8\u0006)"}, d2 = {"Lcom/b3insights/app/viewmodel/PortfolioUiState;", "", "isLoading", "", "items", "", "Lcom/b3insights/app/viewmodel/PortfolioItemUi;", "totalInvested", "", "totalCurrent", "tirAnnualPct", "fxFallback", "errorMessage", "", "(ZLjava/util/List;DDLjava/lang/Double;ZLjava/lang/String;)V", "getErrorMessage", "()Ljava/lang/String;", "getFxFallback", "()Z", "getItems", "()Ljava/util/List;", "getTirAnnualPct", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getTotalCurrent", "()D", "getTotalInvested", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "(ZLjava/util/List;DDLjava/lang/Double;ZLjava/lang/String;)Lcom/b3insights/app/viewmodel/PortfolioUiState;", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class PortfolioUiState {
    private final boolean isLoading = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.b3insights.app.viewmodel.PortfolioItemUi> items = null;
    private final double totalInvested = 0.0;
    private final double totalCurrent = 0.0;
    
    /**
     * TIR anualizada (XIRR) da carteira em % a.a. — aportes, vendas e valor atual
     */
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double tirAnnualPct = null;
    
    /**
     * true se USD/BRL falhou e usamos 5,0
     */
    private final boolean fxFallback = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    
    public PortfolioUiState(boolean isLoading, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.viewmodel.PortfolioItemUi> items, double totalInvested, double totalCurrent, @org.jetbrains.annotations.Nullable()
    java.lang.Double tirAnnualPct, boolean fxFallback, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.viewmodel.PortfolioItemUi> getItems() {
        return null;
    }
    
    public final double getTotalInvested() {
        return 0.0;
    }
    
    public final double getTotalCurrent() {
        return 0.0;
    }
    
    /**
     * TIR anualizada (XIRR) da carteira em % a.a. — aportes, vendas e valor atual
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getTirAnnualPct() {
        return null;
    }
    
    /**
     * true se USD/BRL falhou e usamos 5,0
     */
    public final boolean getFxFallback() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    public PortfolioUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.viewmodel.PortfolioItemUi> component2() {
        return null;
    }
    
    public final double component3() {
        return 0.0;
    }
    
    public final double component4() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.viewmodel.PortfolioUiState copy(boolean isLoading, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.viewmodel.PortfolioItemUi> items, double totalInvested, double totalCurrent, @org.jetbrains.annotations.Nullable()
    java.lang.Double tirAnnualPct, boolean fxFallback, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}