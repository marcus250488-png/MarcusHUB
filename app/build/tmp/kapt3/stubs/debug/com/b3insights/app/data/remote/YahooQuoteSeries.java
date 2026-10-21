package com.b3insights.app.data.remote;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BU\u0012\u0012\b\u0002\u0010\u0002\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003\u0012\u0012\b\u0002\u0010\u0005\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003\u0012\u0012\b\u0002\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003\u0012\u0012\b\u0002\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\tJ\u0013\u0010\u000f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0013\u0010\u0010\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0013\u0010\u0011\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0013\u0010\u0012\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0003H\u00c6\u0003JY\u0010\u0013\u001a\u00020\u00002\u0012\b\u0002\u0010\u0002\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u00032\u0012\b\u0002\u0010\u0005\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u00032\u0012\b\u0002\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u00032\u0012\b\u0002\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R \u0010\u0002\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR \u0010\u0005\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR \u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR \u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\b\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000b\u00a8\u0006\u001b"}, d2 = {"Lcom/b3insights/app/data/remote/YahooQuoteSeries;", "", "close", "", "", "high", "low", "volume", "", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getClose", "()Ljava/util/List;", "getHigh", "getLow", "getVolume", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class YahooQuoteSeries {
    @com.google.gson.annotations.SerializedName(value = "close")
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<java.lang.Double> close = null;
    @com.google.gson.annotations.SerializedName(value = "high")
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<java.lang.Double> high = null;
    @com.google.gson.annotations.SerializedName(value = "low")
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<java.lang.Double> low = null;
    @com.google.gson.annotations.SerializedName(value = "volume")
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<java.lang.Long> volume = null;
    
    public YahooQuoteSeries(@org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Double> close, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Double> high, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Double> low, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Long> volume) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.Double> getClose() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.Double> getHigh() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.Double> getLow() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.Long> getVolume() {
        return null;
    }
    
    public YahooQuoteSeries() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.Double> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.Double> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.Double> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.Long> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.remote.YahooQuoteSeries copy(@org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Double> close, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Double> high, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Double> low, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Long> volume) {
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