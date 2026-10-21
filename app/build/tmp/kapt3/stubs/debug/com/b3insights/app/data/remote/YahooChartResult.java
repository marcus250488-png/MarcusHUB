package com.b3insights.app.data.remote;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0002\u0010\u000bJ\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0011\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\nH\u00c6\u0003J?\u0010\u0018\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\nH\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001J\t\u0010\u001e\u001a\u00020\u001fH\u00d6\u0001R\u0018\u0010\u0007\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0018\u0010\t\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0018\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006 "}, d2 = {"Lcom/b3insights/app/data/remote/YahooChartResult;", "", "meta", "Lcom/b3insights/app/data/remote/YahooChartMeta;", "timestamp", "", "", "events", "Lcom/b3insights/app/data/remote/YahooEvents;", "indicators", "Lcom/b3insights/app/data/remote/YahooIndicators;", "(Lcom/b3insights/app/data/remote/YahooChartMeta;Ljava/util/List;Lcom/b3insights/app/data/remote/YahooEvents;Lcom/b3insights/app/data/remote/YahooIndicators;)V", "getEvents", "()Lcom/b3insights/app/data/remote/YahooEvents;", "getIndicators", "()Lcom/b3insights/app/data/remote/YahooIndicators;", "getMeta", "()Lcom/b3insights/app/data/remote/YahooChartMeta;", "getTimestamp", "()Ljava/util/List;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class YahooChartResult {
    @com.google.gson.annotations.SerializedName(value = "meta")
    @org.jetbrains.annotations.Nullable()
    private final com.b3insights.app.data.remote.YahooChartMeta meta = null;
    @com.google.gson.annotations.SerializedName(value = "timestamp")
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<java.lang.Long> timestamp = null;
    @com.google.gson.annotations.SerializedName(value = "events")
    @org.jetbrains.annotations.Nullable()
    private final com.b3insights.app.data.remote.YahooEvents events = null;
    @com.google.gson.annotations.SerializedName(value = "indicators")
    @org.jetbrains.annotations.Nullable()
    private final com.b3insights.app.data.remote.YahooIndicators indicators = null;
    
    public YahooChartResult(@org.jetbrains.annotations.Nullable()
    com.b3insights.app.data.remote.YahooChartMeta meta, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Long> timestamp, @org.jetbrains.annotations.Nullable()
    com.b3insights.app.data.remote.YahooEvents events, @org.jetbrains.annotations.Nullable()
    com.b3insights.app.data.remote.YahooIndicators indicators) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.b3insights.app.data.remote.YahooChartMeta getMeta() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.Long> getTimestamp() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.b3insights.app.data.remote.YahooEvents getEvents() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.b3insights.app.data.remote.YahooIndicators getIndicators() {
        return null;
    }
    
    public YahooChartResult() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.b3insights.app.data.remote.YahooChartMeta component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.Long> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.b3insights.app.data.remote.YahooEvents component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.b3insights.app.data.remote.YahooIndicators component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.remote.YahooChartResult copy(@org.jetbrains.annotations.Nullable()
    com.b3insights.app.data.remote.YahooChartMeta meta, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Long> timestamp, @org.jetbrains.annotations.Nullable()
    com.b3insights.app.data.remote.YahooEvents events, @org.jetbrains.annotations.Nullable()
    com.b3insights.app.data.remote.YahooIndicators indicators) {
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