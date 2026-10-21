package com.b3insights.app.viewmodel;

/**
 * Posição agregada por ticker, já somando as múltiplas linhas de aporte (preço médio ponderado).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b-\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u009d\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0016J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010.\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003\u00a2\u0006\u0002\u0010!J\u000f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u00c6\u0003J\u0010\u00100\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001cJ\u0010\u00101\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001cJ\u0010\u00102\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001cJ\u0010\u00103\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001cJ\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\t\u00105\u001a\u00020\u0006H\u00c6\u0003J\t\u00106\u001a\u00020\u0006H\u00c6\u0003J\t\u00107\u001a\u00020\u0006H\u00c6\u0003J\u0010\u00108\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001cJ\u0010\u00109\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001cJ\u0010\u0010:\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001cJ\u0010\u0010;\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001cJ\u00bc\u0001\u0010<\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0006H\u00c6\u0001\u00a2\u0006\u0002\u0010=J\u0013\u0010>\u001a\u00020?2\b\u0010@\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010A\u001a\u00020BH\u00d6\u0001J\t\u0010C\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0015\u0010\t\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b\u001b\u0010\u001cR\u0015\u0010\n\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b\u001e\u0010\u001cR\u0015\u0010\u0013\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b\u001f\u0010\u001cR\u0015\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\n\n\u0002\u0010\"\u001a\u0004\b \u0010!R\u0015\u0010\u0015\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b#\u0010\u001cR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b&\u0010\u001cR\u0015\u0010\f\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b\'\u0010\u001cR\u0015\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b(\u0010\u001cR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0018R\u0011\u0010\b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001aR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001aR\u0015\u0010\u0014\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b,\u0010\u001c\u00a8\u0006D"}, d2 = {"Lcom/b3insights/app/viewmodel/PortfolioItemUi;", "", "ticker", "", "assetType", "totalQuantity", "", "averagePrice", "totalInvested", "currentPrice", "currentValue", "profitLoss", "profitLossPercent", "lastPurchaseDate", "", "positions", "", "Lcom/b3insights/app/data/local/entity/PortfolioPositionEntity;", "pvp", "dividendYield", "yieldOnCost", "pl", "(Ljava/lang/String;Ljava/lang/String;DDDLjava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Long;Ljava/util/List;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;)V", "getAssetType", "()Ljava/lang/String;", "getAveragePrice", "()D", "getCurrentPrice", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getCurrentValue", "getDividendYield", "getLastPurchaseDate", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getPl", "getPositions", "()Ljava/util/List;", "getProfitLoss", "getProfitLossPercent", "getPvp", "getTicker", "getTotalInvested", "getTotalQuantity", "getYieldOnCost", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;DDDLjava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Long;Ljava/util/List;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;)Lcom/b3insights/app/viewmodel/PortfolioItemUi;", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class PortfolioItemUi {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String ticker = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String assetType = null;
    private final double totalQuantity = 0.0;
    private final double averagePrice = 0.0;
    private final double totalInvested = 0.0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double currentPrice = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double currentValue = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double profitLoss = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double profitLossPercent = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long lastPurchaseDate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> positions = null;
    
    /**
     * P/VP quando a fonte retornar (ações/FIIs).
     */
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double pvp = null;
    
    /**
     * Dividend yield atual % (sobre cotação).
     */
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double dividendYield = null;
    
    /**
     * Yield on cost % ≈ (renda anual estimada) / valor investido × 100.
     * Renda anual ≈ valor atual × (DY/100) quando DY disponível.
     */
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double yieldOnCost = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double pl = null;
    
    public PortfolioItemUi(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.NotNull()
    java.lang.String assetType, double totalQuantity, double averagePrice, double totalInvested, @org.jetbrains.annotations.Nullable()
    java.lang.Double currentPrice, @org.jetbrains.annotations.Nullable()
    java.lang.Double currentValue, @org.jetbrains.annotations.Nullable()
    java.lang.Double profitLoss, @org.jetbrains.annotations.Nullable()
    java.lang.Double profitLossPercent, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastPurchaseDate, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> positions, @org.jetbrains.annotations.Nullable()
    java.lang.Double pvp, @org.jetbrains.annotations.Nullable()
    java.lang.Double dividendYield, @org.jetbrains.annotations.Nullable()
    java.lang.Double yieldOnCost, @org.jetbrains.annotations.Nullable()
    java.lang.Double pl) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTicker() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAssetType() {
        return null;
    }
    
    public final double getTotalQuantity() {
        return 0.0;
    }
    
    public final double getAveragePrice() {
        return 0.0;
    }
    
    public final double getTotalInvested() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getCurrentPrice() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getCurrentValue() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getProfitLoss() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getProfitLossPercent() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getLastPurchaseDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> getPositions() {
        return null;
    }
    
    /**
     * P/VP quando a fonte retornar (ações/FIIs).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPvp() {
        return null;
    }
    
    /**
     * Dividend yield atual % (sobre cotação).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getDividendYield() {
        return null;
    }
    
    /**
     * Yield on cost % ≈ (renda anual estimada) / valor investido × 100.
     * Renda anual ≈ valor atual × (DY/100) quando DY disponível.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getYieldOnCost() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final double component3() {
        return 0.0;
    }
    
    public final double component4() {
        return 0.0;
    }
    
    public final double component5() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.viewmodel.PortfolioItemUi copy(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.NotNull()
    java.lang.String assetType, double totalQuantity, double averagePrice, double totalInvested, @org.jetbrains.annotations.Nullable()
    java.lang.Double currentPrice, @org.jetbrains.annotations.Nullable()
    java.lang.Double currentValue, @org.jetbrains.annotations.Nullable()
    java.lang.Double profitLoss, @org.jetbrains.annotations.Nullable()
    java.lang.Double profitLossPercent, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastPurchaseDate, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> positions, @org.jetbrains.annotations.Nullable()
    java.lang.Double pvp, @org.jetbrains.annotations.Nullable()
    java.lang.Double dividendYield, @org.jetbrains.annotations.Nullable()
    java.lang.Double yieldOnCost, @org.jetbrains.annotations.Nullable()
    java.lang.Double pl) {
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