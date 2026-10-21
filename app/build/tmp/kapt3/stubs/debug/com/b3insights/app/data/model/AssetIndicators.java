package com.b3insights.app.data.model;

/**
 * Indicadores fundamentalistas — nulos quando a fonte não fornece o dado.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0013\n\u0002\u0010\b\n\u0002\bB\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u00bd\u0002\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0017\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u001eJ\u0010\u0010=\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010>\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010?\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010@\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010A\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010B\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010C\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010D\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010E\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010F\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010G\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010H\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010I\u001a\u0004\u0018\u00010\u0017H\u00c6\u0003\u00a2\u0006\u0002\u0010/J\u0010\u0010J\u001a\u0004\u0018\u00010\u0017H\u00c6\u0003\u00a2\u0006\u0002\u0010/J\u0010\u0010K\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010L\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010M\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010N\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010O\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010P\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010Q\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010R\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010S\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010T\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010U\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0010\u0010V\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u00c6\u0002\u0010W\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00172\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00172\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010XJ\u0013\u0010Y\u001a\u00020Z2\b\u0010[\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\\\u001a\u00020\u0017H\u00d6\u0001J\t\u0010]\u001a\u00020^H\u00d6\u0001R\u0015\u0010\u001b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b\u001f\u0010 R\u0015\u0010\u0019\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b\"\u0010 R\u0015\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b#\u0010 R\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b$\u0010 R\u0015\u0010\t\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b%\u0010 R\u0015\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b&\u0010 R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b\'\u0010 R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b(\u0010 R\u0015\u0010\u001a\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b)\u0010 R\u0015\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b*\u0010 R\u0015\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b+\u0010 R\u0015\u0010\b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b,\u0010 R\u0015\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b-\u0010 R\u0015\u0010\u0018\u001a\u0004\u0018\u00010\u0017\u00a2\u0006\n\n\u0002\u00100\u001a\u0004\b.\u0010/R\u0015\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u00a2\u0006\n\n\u0002\u00100\u001a\u0004\b1\u0010/R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b2\u0010 R\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b3\u0010 R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b4\u0010 R\u0015\u0010\u001d\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b5\u0010 R\u0015\u0010\u001c\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b6\u0010 R\u0015\u0010\r\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b7\u0010 R\u0015\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b8\u0010 R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b9\u0010 R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b:\u0010 R\u0015\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b;\u0010 R\u0015\u0010\f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b<\u0010 \u00a8\u0006_"}, d2 = {"Lcom/b3insights/app/data/model/AssetIndicators;", "", "pl", "", "pvp", "roe", "roic", "dividendYield", "netMargin", "ebitdaMargin", "netDebtToEbitda", "lpa", "vpa", "revenue", "netIncome", "revenueGrowth", "profitGrowth", "marketCap", "freeFloat", "dividendPerShare", "vacancy", "netWorth", "numberOfShareholders", "", "numberOfProperties", "capRate", "monthlyYield", "annualYield", "quotaPrice", "quotaBookValue", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;)V", "getAnnualYield", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getCapRate", "getDividendPerShare", "getDividendYield", "getEbitdaMargin", "getFreeFloat", "getLpa", "getMarketCap", "getMonthlyYield", "getNetDebtToEbitda", "getNetIncome", "getNetMargin", "getNetWorth", "getNumberOfProperties", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getNumberOfShareholders", "getPl", "getProfitGrowth", "getPvp", "getQuotaBookValue", "getQuotaPrice", "getRevenue", "getRevenueGrowth", "getRoe", "getRoic", "getVacancy", "getVpa", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;)Lcom/b3insights/app/data/model/AssetIndicators;", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class AssetIndicators {
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double pl = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double pvp = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double roe = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double roic = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double dividendYield = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double netMargin = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double ebitdaMargin = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double netDebtToEbitda = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double lpa = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double vpa = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double revenue = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double netIncome = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double revenueGrowth = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double profitGrowth = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double marketCap = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double freeFloat = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double dividendPerShare = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double vacancy = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double netWorth = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer numberOfShareholders = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer numberOfProperties = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double capRate = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double monthlyYield = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double annualYield = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double quotaPrice = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double quotaBookValue = null;
    
    public AssetIndicators(@org.jetbrains.annotations.Nullable()
    java.lang.Double pl, @org.jetbrains.annotations.Nullable()
    java.lang.Double pvp, @org.jetbrains.annotations.Nullable()
    java.lang.Double roe, @org.jetbrains.annotations.Nullable()
    java.lang.Double roic, @org.jetbrains.annotations.Nullable()
    java.lang.Double dividendYield, @org.jetbrains.annotations.Nullable()
    java.lang.Double netMargin, @org.jetbrains.annotations.Nullable()
    java.lang.Double ebitdaMargin, @org.jetbrains.annotations.Nullable()
    java.lang.Double netDebtToEbitda, @org.jetbrains.annotations.Nullable()
    java.lang.Double lpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double vpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double revenue, @org.jetbrains.annotations.Nullable()
    java.lang.Double netIncome, @org.jetbrains.annotations.Nullable()
    java.lang.Double revenueGrowth, @org.jetbrains.annotations.Nullable()
    java.lang.Double profitGrowth, @org.jetbrains.annotations.Nullable()
    java.lang.Double marketCap, @org.jetbrains.annotations.Nullable()
    java.lang.Double freeFloat, @org.jetbrains.annotations.Nullable()
    java.lang.Double dividendPerShare, @org.jetbrains.annotations.Nullable()
    java.lang.Double vacancy, @org.jetbrains.annotations.Nullable()
    java.lang.Double netWorth, @org.jetbrains.annotations.Nullable()
    java.lang.Integer numberOfShareholders, @org.jetbrains.annotations.Nullable()
    java.lang.Integer numberOfProperties, @org.jetbrains.annotations.Nullable()
    java.lang.Double capRate, @org.jetbrains.annotations.Nullable()
    java.lang.Double monthlyYield, @org.jetbrains.annotations.Nullable()
    java.lang.Double annualYield, @org.jetbrains.annotations.Nullable()
    java.lang.Double quotaPrice, @org.jetbrains.annotations.Nullable()
    java.lang.Double quotaBookValue) {
        super();
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
    public final java.lang.Double getRoe() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getRoic() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getDividendYield() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getNetMargin() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getEbitdaMargin() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getNetDebtToEbitda() {
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
    public final java.lang.Double getRevenue() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getNetIncome() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getRevenueGrowth() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getProfitGrowth() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMarketCap() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getFreeFloat() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getDividendPerShare() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getVacancy() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getNetWorth() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getNumberOfShareholders() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getNumberOfProperties() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getCapRate() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMonthlyYield() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getAnnualYield() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getQuotaPrice() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getQuotaBookValue() {
        return null;
    }
    
    public AssetIndicators() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component1() {
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
    public final java.lang.Double component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component20() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component21() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component22() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component23() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component24() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component25() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component26() {
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
    public final com.b3insights.app.data.model.AssetIndicators copy(@org.jetbrains.annotations.Nullable()
    java.lang.Double pl, @org.jetbrains.annotations.Nullable()
    java.lang.Double pvp, @org.jetbrains.annotations.Nullable()
    java.lang.Double roe, @org.jetbrains.annotations.Nullable()
    java.lang.Double roic, @org.jetbrains.annotations.Nullable()
    java.lang.Double dividendYield, @org.jetbrains.annotations.Nullable()
    java.lang.Double netMargin, @org.jetbrains.annotations.Nullable()
    java.lang.Double ebitdaMargin, @org.jetbrains.annotations.Nullable()
    java.lang.Double netDebtToEbitda, @org.jetbrains.annotations.Nullable()
    java.lang.Double lpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double vpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double revenue, @org.jetbrains.annotations.Nullable()
    java.lang.Double netIncome, @org.jetbrains.annotations.Nullable()
    java.lang.Double revenueGrowth, @org.jetbrains.annotations.Nullable()
    java.lang.Double profitGrowth, @org.jetbrains.annotations.Nullable()
    java.lang.Double marketCap, @org.jetbrains.annotations.Nullable()
    java.lang.Double freeFloat, @org.jetbrains.annotations.Nullable()
    java.lang.Double dividendPerShare, @org.jetbrains.annotations.Nullable()
    java.lang.Double vacancy, @org.jetbrains.annotations.Nullable()
    java.lang.Double netWorth, @org.jetbrains.annotations.Nullable()
    java.lang.Integer numberOfShareholders, @org.jetbrains.annotations.Nullable()
    java.lang.Integer numberOfProperties, @org.jetbrains.annotations.Nullable()
    java.lang.Double capRate, @org.jetbrains.annotations.Nullable()
    java.lang.Double monthlyYield, @org.jetbrains.annotations.Nullable()
    java.lang.Double annualYield, @org.jetbrains.annotations.Nullable()
    java.lang.Double quotaPrice, @org.jetbrains.annotations.Nullable()
    java.lang.Double quotaBookValue) {
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