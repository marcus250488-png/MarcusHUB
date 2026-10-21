package com.b3insights.app.data.remote;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u001f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B}\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\u0002\u0010\u0010J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u0010#\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001aJ\u0010\u0010$\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010%\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010&\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010\'\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010(\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010)\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010*\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010+\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010 J\u0086\u0001\u0010,\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00c6\u0001\u00a2\u0006\u0002\u0010-J\u0013\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00101\u001a\u00020\rH\u00d6\u0001J\t\u00102\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\b\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\t\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0014\u0010\u0012R\u001a\u0010\n\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0015\u0010\u0012R\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0016\u0010\u0012R\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0017\u0010\u0012R\u001a\u0010\u0007\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0018\u0010\u0012R\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u000f8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b\u0019\u0010\u001aR\u0018\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u001a\u0010\u000b\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u001e\u0010\u0012R\u001a\u0010\f\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b\u001f\u0010 \u00a8\u00063"}, d2 = {"Lcom/b3insights/app/data/remote/BrapiFiiIndicatorDto;", "", "symbol", "", "price", "", "navPerShare", "priceToNav", "dividendYield12m", "dividendYield1m", "equity", "totalAssets", "totalInvestors", "", "sharesOutstanding", "", "(Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Integer;Ljava/lang/Long;)V", "getDividendYield12m", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getDividendYield1m", "getEquity", "getNavPerShare", "getPrice", "getPriceToNav", "getSharesOutstanding", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getSymbol", "()Ljava/lang/String;", "getTotalAssets", "getTotalInvestors", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Integer;Ljava/lang/Long;)Lcom/b3insights/app/data/remote/BrapiFiiIndicatorDto;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class BrapiFiiIndicatorDto {
    @com.google.gson.annotations.SerializedName(value = "symbol")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String symbol = null;
    @com.google.gson.annotations.SerializedName(value = "price")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double price = null;
    @com.google.gson.annotations.SerializedName(value = "navPerShare")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double navPerShare = null;
    @com.google.gson.annotations.SerializedName(value = "priceToNav")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double priceToNav = null;
    @com.google.gson.annotations.SerializedName(value = "dividendYield12m")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double dividendYield12m = null;
    @com.google.gson.annotations.SerializedName(value = "dividendYield1m")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double dividendYield1m = null;
    @com.google.gson.annotations.SerializedName(value = "equity")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double equity = null;
    @com.google.gson.annotations.SerializedName(value = "totalAssets")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double totalAssets = null;
    @com.google.gson.annotations.SerializedName(value = "totalInvestors")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer totalInvestors = null;
    @com.google.gson.annotations.SerializedName(value = "sharesOutstanding")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long sharesOutstanding = null;
    
    public BrapiFiiIndicatorDto(@org.jetbrains.annotations.Nullable()
    java.lang.String symbol, @org.jetbrains.annotations.Nullable()
    java.lang.Double price, @org.jetbrains.annotations.Nullable()
    java.lang.Double navPerShare, @org.jetbrains.annotations.Nullable()
    java.lang.Double priceToNav, @org.jetbrains.annotations.Nullable()
    java.lang.Double dividendYield12m, @org.jetbrains.annotations.Nullable()
    java.lang.Double dividendYield1m, @org.jetbrains.annotations.Nullable()
    java.lang.Double equity, @org.jetbrains.annotations.Nullable()
    java.lang.Double totalAssets, @org.jetbrains.annotations.Nullable()
    java.lang.Integer totalInvestors, @org.jetbrains.annotations.Nullable()
    java.lang.Long sharesOutstanding) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSymbol() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPrice() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getNavPerShare() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPriceToNav() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getDividendYield12m() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getDividendYield1m() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getEquity() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getTotalAssets() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getTotalInvestors() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getSharesOutstanding() {
        return null;
    }
    
    public BrapiFiiIndicatorDto() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component2() {
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
    public final java.lang.Integer component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.remote.BrapiFiiIndicatorDto copy(@org.jetbrains.annotations.Nullable()
    java.lang.String symbol, @org.jetbrains.annotations.Nullable()
    java.lang.Double price, @org.jetbrains.annotations.Nullable()
    java.lang.Double navPerShare, @org.jetbrains.annotations.Nullable()
    java.lang.Double priceToNav, @org.jetbrains.annotations.Nullable()
    java.lang.Double dividendYield12m, @org.jetbrains.annotations.Nullable()
    java.lang.Double dividendYield1m, @org.jetbrains.annotations.Nullable()
    java.lang.Double equity, @org.jetbrains.annotations.Nullable()
    java.lang.Double totalAssets, @org.jetbrains.annotations.Nullable()
    java.lang.Integer totalInvestors, @org.jetbrains.annotations.Nullable()
    java.lang.Long sharesOutstanding) {
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