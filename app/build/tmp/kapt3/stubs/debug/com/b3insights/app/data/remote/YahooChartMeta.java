package com.b3insights.app.data.remote;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b(\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u00ad\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\u0002\u0010\u0013J\u000b\u0010\'\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u0010)\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003\u00a2\u0006\u0002\u0010\"J\u0010\u0010*\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010+\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010,\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003\u00a2\u0006\u0002\u0010\"J\u000b\u0010-\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u00100\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u00101\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u00102\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u00103\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u000b\u00104\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u00b6\u0001\u00105\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u000fH\u00c6\u0001\u00a2\u0006\u0002\u00106J\u0013\u00107\u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010:\u001a\u00020;H\u00d6\u0001J\t\u0010<\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\n\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0014\u0010\u0015R\u0018\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u0018\u0010\r\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0018R\u001a\u0010\u000b\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u001c\u0010\u0015R\u001a\u0010\t\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u001d\u0010\u0015R\u001a\u0010\u0010\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u001e\u0010\u0015R\u001a\u0010\u0011\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u001f\u0010\u0015R\u001a\u0010\u0007\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b \u0010\u0015R\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u000f8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010#\u001a\u0004\b!\u0010\"R\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u000f8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010#\u001a\u0004\b$\u0010\"R\u0018\u0010\f\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0018R\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0018\u00a8\u0006="}, d2 = {"Lcom/b3insights/app/data/remote/YahooChartMeta;", "", "currency", "", "symbol", "exchangeName", "instrumentType", "regularMarketPrice", "", "regularMarketChangePercent", "chartPreviousClose", "previousClose", "shortName", "longName", "regularMarketTime", "", "regularMarketDayHigh", "regularMarketDayLow", "regularMarketVolume", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Long;)V", "getChartPreviousClose", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getCurrency", "()Ljava/lang/String;", "getExchangeName", "getInstrumentType", "getLongName", "getPreviousClose", "getRegularMarketChangePercent", "getRegularMarketDayHigh", "getRegularMarketDayLow", "getRegularMarketPrice", "getRegularMarketTime", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getRegularMarketVolume", "getShortName", "getSymbol", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Long;)Lcom/b3insights/app/data/remote/YahooChartMeta;", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class YahooChartMeta {
    @com.google.gson.annotations.SerializedName(value = "currency")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String currency = null;
    @com.google.gson.annotations.SerializedName(value = "symbol")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String symbol = null;
    @com.google.gson.annotations.SerializedName(value = "exchangeName")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String exchangeName = null;
    @com.google.gson.annotations.SerializedName(value = "instrumentType")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String instrumentType = null;
    @com.google.gson.annotations.SerializedName(value = "regularMarketPrice")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double regularMarketPrice = null;
    @com.google.gson.annotations.SerializedName(value = "regularMarketChangePercent")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double regularMarketChangePercent = null;
    @com.google.gson.annotations.SerializedName(value = "chartPreviousClose")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double chartPreviousClose = null;
    @com.google.gson.annotations.SerializedName(value = "previousClose")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double previousClose = null;
    @com.google.gson.annotations.SerializedName(value = "shortName")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String shortName = null;
    @com.google.gson.annotations.SerializedName(value = "longName")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String longName = null;
    @com.google.gson.annotations.SerializedName(value = "regularMarketTime")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long regularMarketTime = null;
    @com.google.gson.annotations.SerializedName(value = "regularMarketDayHigh")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double regularMarketDayHigh = null;
    @com.google.gson.annotations.SerializedName(value = "regularMarketDayLow")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double regularMarketDayLow = null;
    @com.google.gson.annotations.SerializedName(value = "regularMarketVolume")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long regularMarketVolume = null;
    
    public YahooChartMeta(@org.jetbrains.annotations.Nullable()
    java.lang.String currency, @org.jetbrains.annotations.Nullable()
    java.lang.String symbol, @org.jetbrains.annotations.Nullable()
    java.lang.String exchangeName, @org.jetbrains.annotations.Nullable()
    java.lang.String instrumentType, @org.jetbrains.annotations.Nullable()
    java.lang.Double regularMarketPrice, @org.jetbrains.annotations.Nullable()
    java.lang.Double regularMarketChangePercent, @org.jetbrains.annotations.Nullable()
    java.lang.Double chartPreviousClose, @org.jetbrains.annotations.Nullable()
    java.lang.Double previousClose, @org.jetbrains.annotations.Nullable()
    java.lang.String shortName, @org.jetbrains.annotations.Nullable()
    java.lang.String longName, @org.jetbrains.annotations.Nullable()
    java.lang.Long regularMarketTime, @org.jetbrains.annotations.Nullable()
    java.lang.Double regularMarketDayHigh, @org.jetbrains.annotations.Nullable()
    java.lang.Double regularMarketDayLow, @org.jetbrains.annotations.Nullable()
    java.lang.Long regularMarketVolume) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCurrency() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSymbol() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getExchangeName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getInstrumentType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getRegularMarketPrice() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getRegularMarketChangePercent() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getChartPreviousClose() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPreviousClose() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getShortName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLongName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getRegularMarketTime() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getRegularMarketDayHigh() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getRegularMarketDayLow() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getRegularMarketVolume() {
        return null;
    }
    
    public YahooChartMeta() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component11() {
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
    public final java.lang.Long component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
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
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.remote.YahooChartMeta copy(@org.jetbrains.annotations.Nullable()
    java.lang.String currency, @org.jetbrains.annotations.Nullable()
    java.lang.String symbol, @org.jetbrains.annotations.Nullable()
    java.lang.String exchangeName, @org.jetbrains.annotations.Nullable()
    java.lang.String instrumentType, @org.jetbrains.annotations.Nullable()
    java.lang.Double regularMarketPrice, @org.jetbrains.annotations.Nullable()
    java.lang.Double regularMarketChangePercent, @org.jetbrains.annotations.Nullable()
    java.lang.Double chartPreviousClose, @org.jetbrains.annotations.Nullable()
    java.lang.Double previousClose, @org.jetbrains.annotations.Nullable()
    java.lang.String shortName, @org.jetbrains.annotations.Nullable()
    java.lang.String longName, @org.jetbrains.annotations.Nullable()
    java.lang.Long regularMarketTime, @org.jetbrains.annotations.Nullable()
    java.lang.Double regularMarketDayHigh, @org.jetbrains.annotations.Nullable()
    java.lang.Double regularMarketDayLow, @org.jetbrains.annotations.Nullable()
    java.lang.Long regularMarketVolume) {
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