package com.b3insights.app.ui.screens.ranking;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\bK\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0097\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u001dJ\t\u0010;\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010<\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010=\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010>\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010?\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010@\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010A\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010B\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010C\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010D\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010E\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u000b\u0010F\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u0010G\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u000b\u0010H\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u0010I\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u000b\u0010J\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010K\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u0010L\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010M\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010N\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010O\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00108J\u0010\u0010P\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010Q\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010R\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u00ac\u0002\u0010S\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010TJ\u0013\u0010U\u001a\u00020V2\b\u0010W\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010X\u001a\u00020YH\u00d6\u0001J\t\u0010Z\u001a\u00020\u0003H\u00d6\u0001R\u0015\u0010\u0018\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0015\u0010\u0015\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b#\u0010\u001fR\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b$\u0010\u001fR\u0015\u0010\r\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b%\u0010\u001fR\u0015\u0010\u0014\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b&\u0010\u001fR\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\"R\u0015\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b(\u0010\u001fR\u0015\u0010\u0017\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b)\u0010\u001fR\u0015\u0010\u0016\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b*\u0010\u001fR\u0015\u0010\b\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b+\u0010\u001fR\u0013\u0010\u001b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\"R\u0015\u0010\u001a\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b-\u0010\u001fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010\"R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b/\u0010\u001fR\u0015\u0010\u000f\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b0\u0010\u001fR\u0015\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b1\u0010\u001fR\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b2\u0010\u001fR\u0015\u0010\f\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b3\u0010\u001fR\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b4\u0010\u001fR\u0015\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b5\u0010\u001fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010\"R\u0015\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u00109\u001a\u0004\b7\u00108R\u0015\u0010\u0013\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b:\u0010\u001f\u00a8\u0006["}, d2 = {"Lcom/b3insights/app/ui/screens/ranking/RankingRow;", "", "ticker", "", "name", "price", "", "changePercent", "marketCap", "volume", "", "pl", "pvp", "dy", "roe", "netMargin", "revenue", "netIncome", "lpa", "vpa", "grahamPrice", "bazinPrice", "marginGraham", "marginBazin", "bazinDps", "bazinPeriod", "metricValue", "metricLabel", "logoUrl", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Long;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;)V", "getBazinDps", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getBazinPeriod", "()Ljava/lang/String;", "getBazinPrice", "getChangePercent", "getDy", "getGrahamPrice", "getLogoUrl", "getLpa", "getMarginBazin", "getMarginGraham", "getMarketCap", "getMetricLabel", "getMetricValue", "getName", "getNetIncome", "getNetMargin", "getPl", "getPrice", "getPvp", "getRevenue", "getRoe", "getTicker", "getVolume", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getVpa", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Long;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;)Lcom/b3insights/app/ui/screens/ranking/RankingRow;", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class RankingRow {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String ticker = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double price = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double changePercent = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double marketCap = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long volume = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double pl = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double pvp = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double dy = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double roe = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double netMargin = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double revenue = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double netIncome = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double lpa = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double vpa = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double grahamPrice = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double bazinPrice = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double marginGraham = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double marginBazin = null;
    
    /**
     * DPS anual (R$) usado no Bazin
     */
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double bazinDps = null;
    
    /**
     * Origem do DPS (ex.: LTM via yield)
     */
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String bazinPeriod = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double metricValue = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String metricLabel = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String logoUrl = null;
    
    public RankingRow(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.Double price, @org.jetbrains.annotations.Nullable()
    java.lang.Double changePercent, @org.jetbrains.annotations.Nullable()
    java.lang.Double marketCap, @org.jetbrains.annotations.Nullable()
    java.lang.Long volume, @org.jetbrains.annotations.Nullable()
    java.lang.Double pl, @org.jetbrains.annotations.Nullable()
    java.lang.Double pvp, @org.jetbrains.annotations.Nullable()
    java.lang.Double dy, @org.jetbrains.annotations.Nullable()
    java.lang.Double roe, @org.jetbrains.annotations.Nullable()
    java.lang.Double netMargin, @org.jetbrains.annotations.Nullable()
    java.lang.Double revenue, @org.jetbrains.annotations.Nullable()
    java.lang.Double netIncome, @org.jetbrains.annotations.Nullable()
    java.lang.Double lpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double vpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double grahamPrice, @org.jetbrains.annotations.Nullable()
    java.lang.Double bazinPrice, @org.jetbrains.annotations.Nullable()
    java.lang.Double marginGraham, @org.jetbrains.annotations.Nullable()
    java.lang.Double marginBazin, @org.jetbrains.annotations.Nullable()
    java.lang.Double bazinDps, @org.jetbrains.annotations.Nullable()
    java.lang.String bazinPeriod, @org.jetbrains.annotations.Nullable()
    java.lang.Double metricValue, @org.jetbrains.annotations.Nullable()
    java.lang.String metricLabel, @org.jetbrains.annotations.Nullable()
    java.lang.String logoUrl) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTicker() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPrice() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getChangePercent() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMarketCap() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getVolume() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPl() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPvp() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getDy() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getRoe() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getNetMargin() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getRevenue() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getNetIncome() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getLpa() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getVpa() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getGrahamPrice() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getBazinPrice() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMarginGraham() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMarginBazin() {
        return null;
    }
    
    /**
     * DPS anual (R$) usado no Bazin
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getBazinDps() {
        return null;
    }
    
    /**
     * Origem do DPS (ex.: LTM via yield)
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBazinPeriod() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMetricValue() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getMetricLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLogoUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component11() {
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component16() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component17() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component18() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component19() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component20() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component21() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component22() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component23() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component24() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component6() {
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
    public final com.b3insights.app.ui.screens.ranking.RankingRow copy(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.Double price, @org.jetbrains.annotations.Nullable()
    java.lang.Double changePercent, @org.jetbrains.annotations.Nullable()
    java.lang.Double marketCap, @org.jetbrains.annotations.Nullable()
    java.lang.Long volume, @org.jetbrains.annotations.Nullable()
    java.lang.Double pl, @org.jetbrains.annotations.Nullable()
    java.lang.Double pvp, @org.jetbrains.annotations.Nullable()
    java.lang.Double dy, @org.jetbrains.annotations.Nullable()
    java.lang.Double roe, @org.jetbrains.annotations.Nullable()
    java.lang.Double netMargin, @org.jetbrains.annotations.Nullable()
    java.lang.Double revenue, @org.jetbrains.annotations.Nullable()
    java.lang.Double netIncome, @org.jetbrains.annotations.Nullable()
    java.lang.Double lpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double vpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double grahamPrice, @org.jetbrains.annotations.Nullable()
    java.lang.Double bazinPrice, @org.jetbrains.annotations.Nullable()
    java.lang.Double marginGraham, @org.jetbrains.annotations.Nullable()
    java.lang.Double marginBazin, @org.jetbrains.annotations.Nullable()
    java.lang.Double bazinDps, @org.jetbrains.annotations.Nullable()
    java.lang.String bazinPeriod, @org.jetbrains.annotations.Nullable()
    java.lang.Double metricValue, @org.jetbrains.annotations.Nullable()
    java.lang.String metricLabel, @org.jetbrains.annotations.Nullable()
    java.lang.String logoUrl) {
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