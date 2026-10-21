package com.b3insights.app.data.model;

/**
 * Cotação/resumo de um ativo, usado na busca, favoritos e cabeçalho da página do ativo.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\'\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0085\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\t\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\f\u001a\u0004\u0018\u00010\t\u0012\b\u0010\r\u001a\u0004\u0018\u00010\t\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\t\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0013J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010(\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001eJ\u0010\u0010)\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0017J\u0010\u0010*\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001eJ\u000b\u0010+\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010-\u001a\u00020\u0006H\u00c6\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u0010/\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0017J\u0010\u00100\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0017J\u0010\u00101\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0017J\u0010\u00102\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0017J\u0010\u00103\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0017J\u00a6\u0001\u00104\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u00105J\u0013\u00106\u001a\u0002072\b\u00108\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00109\u001a\u00020:H\u00d6\u0001J\t\u0010;\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0015\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\n\n\u0002\u0010\u0018\u001a\u0004\b\u0016\u0010\u0017R\u0015\u0010\n\u001a\u0004\u0018\u00010\t\u00a2\u0006\n\n\u0002\u0010\u0018\u001a\u0004\b\u0019\u0010\u0017R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\t\u00a2\u0006\n\n\u0002\u0010\u0018\u001a\u0004\b\u001a\u0010\u0017R\u0015\u0010\f\u001a\u0004\u0018\u00010\t\u00a2\u0006\n\n\u0002\u0010\u0018\u001a\u0004\b\u001b\u0010\u0017R\u0015\u0010\r\u001a\u0004\u0018\u00010\t\u00a2\u0006\n\n\u0002\u0010\u0018\u001a\u0004\b\u001c\u0010\u0017R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\n\n\u0002\u0010\u001f\u001a\u0004\b\u001d\u0010\u001eR\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0015\u0010\u0010\u001a\u0004\u0018\u00010\t\u00a2\u0006\n\n\u0002\u0010\u0018\u001a\u0004\b\"\u0010\u0017R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010!R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010!R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010!R\u0015\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\n\n\u0002\u0010\u001f\u001a\u0004\b&\u0010\u001e\u00a8\u0006<"}, d2 = {"Lcom/b3insights/app/data/model/AssetQuote;", "", "ticker", "", "name", "assetType", "Lcom/b3insights/app/data/model/AssetType;", "sector", "currentPrice", "", "dayChange", "dayChangePercent", "dayHigh", "dayLow", "volume", "", "marketCap", "lastUpdated", "logoUrl", "(Ljava/lang/String;Ljava/lang/String;Lcom/b3insights/app/data/model/AssetType;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Long;Ljava/lang/Double;Ljava/lang/Long;Ljava/lang/String;)V", "getAssetType", "()Lcom/b3insights/app/data/model/AssetType;", "getCurrentPrice", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getDayChange", "getDayChangePercent", "getDayHigh", "getDayLow", "getLastUpdated", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getLogoUrl", "()Ljava/lang/String;", "getMarketCap", "getName", "getSector", "getTicker", "getVolume", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Lcom/b3insights/app/data/model/AssetType;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Long;Ljava/lang/Double;Ljava/lang/Long;Ljava/lang/String;)Lcom/b3insights/app/data/model/AssetQuote;", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class AssetQuote {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String ticker = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.model.AssetType assetType = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String sector = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double currentPrice = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double dayChange = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double dayChangePercent = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double dayHigh = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double dayLow = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long volume = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double marketCap = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long lastUpdated = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String logoUrl = null;
    
    public AssetQuote(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.model.AssetType assetType, @org.jetbrains.annotations.Nullable()
    java.lang.String sector, @org.jetbrains.annotations.Nullable()
    java.lang.Double currentPrice, @org.jetbrains.annotations.Nullable()
    java.lang.Double dayChange, @org.jetbrains.annotations.Nullable()
    java.lang.Double dayChangePercent, @org.jetbrains.annotations.Nullable()
    java.lang.Double dayHigh, @org.jetbrains.annotations.Nullable()
    java.lang.Double dayLow, @org.jetbrains.annotations.Nullable()
    java.lang.Long volume, @org.jetbrains.annotations.Nullable()
    java.lang.Double marketCap, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastUpdated, @org.jetbrains.annotations.Nullable()
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
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.model.AssetType getAssetType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSector() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getCurrentPrice() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getDayChange() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getDayChangePercent() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getDayHigh() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getDayLow() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getVolume() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMarketCap() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getLastUpdated() {
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
    public final java.lang.Long component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.model.AssetType component3() {
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
    public final java.lang.Double component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.model.AssetQuote copy(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.model.AssetType assetType, @org.jetbrains.annotations.Nullable()
    java.lang.String sector, @org.jetbrains.annotations.Nullable()
    java.lang.Double currentPrice, @org.jetbrains.annotations.Nullable()
    java.lang.Double dayChange, @org.jetbrains.annotations.Nullable()
    java.lang.Double dayChangePercent, @org.jetbrains.annotations.Nullable()
    java.lang.Double dayHigh, @org.jetbrains.annotations.Nullable()
    java.lang.Double dayLow, @org.jetbrains.annotations.Nullable()
    java.lang.Long volume, @org.jetbrains.annotations.Nullable()
    java.lang.Double marketCap, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastUpdated, @org.jetbrains.annotations.Nullable()
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