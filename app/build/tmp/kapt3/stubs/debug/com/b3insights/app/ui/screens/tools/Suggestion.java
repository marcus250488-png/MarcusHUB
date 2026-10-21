package com.b3insights.app.ui.screens.tools;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b2\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0082\b\u0018\u00002\u00020\u0001B\u00b5\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\u000e\u001a\u00020\u0007\u0012\u0006\u0010\u000f\u001a\u00020\u0007\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0003\u0012\u0006\u0010\u0013\u001a\u00020\u0003\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0016\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0019J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\u0010\u00104\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001dJ\t\u00105\u001a\u00020\u0007H\u00c6\u0003J\t\u00106\u001a\u00020\u0007H\u00c6\u0003J\t\u00107\u001a\u00020\u0011H\u00c6\u0003J\t\u00108\u001a\u00020\u0003H\u00c6\u0003J\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010:\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001dJ\u000f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00030\u0016H\u00c6\u0003J\u0010\u0010<\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001dJ\u000b\u0010=\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010>\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010?\u001a\u00020\u0003H\u00c6\u0003J\t\u0010@\u001a\u00020\u0007H\u00c6\u0003J\t\u0010A\u001a\u00020\u0007H\u00c6\u0003J\u0010\u0010B\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001dJ\u0010\u0010C\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001dJ\u0010\u0010D\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001dJ\u0010\u0010E\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001dJ\u00e4\u0001\u0010F\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u000e\u001a\u00020\u00072\b\b\u0002\u0010\u000f\u001a\u00020\u00072\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00072\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u00162\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010GJ\u0013\u0010H\u001a\u00020I2\b\u0010J\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010K\u001a\u00020\u0011H\u00d6\u0001J\t\u0010L\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u000f\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0015\u0010\u0014\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0015\u0010\n\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b!\u0010\u001dR\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010 R\u0011\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010 R\u0011\u0010\u000e\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001bR\u0015\u0010\u0017\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b%\u0010\u001dR\u0015\u0010\t\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b&\u0010\u001dR\u0015\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b\'\u0010\u001dR\u0015\u0010\f\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b(\u0010\u001dR\u0015\u0010\r\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b)\u0010\u001dR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010 R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001bR\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010\u001bR\u0011\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010 R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010 \u00a8\u0006M"}, d2 = {"Lcom/b3insights/app/ui/screens/tools/Suggestion;", "", "ticker", "", "name", "assetKind", "price", "", "score", "dy", "bazin", "graham", "marginBazin", "marginGraham", "currentWeight", "afterWeight", "sharesAffordable", "", "sector", "corrHint", "annualIncome", "reasons", "", "dpsUsed", "bazinNote", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DDLjava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;DDILjava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/util/List;Ljava/lang/Double;Ljava/lang/String;)V", "getAfterWeight", "()D", "getAnnualIncome", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getAssetKind", "()Ljava/lang/String;", "getBazin", "getBazinNote", "getCorrHint", "getCurrentWeight", "getDpsUsed", "getDy", "getGraham", "getMarginBazin", "getMarginGraham", "getName", "getPrice", "getReasons", "()Ljava/util/List;", "getScore", "getSector", "getSharesAffordable", "()I", "getTicker", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DDLjava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;DDILjava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/util/List;Ljava/lang/Double;Ljava/lang/String;)Lcom/b3insights/app/ui/screens/tools/Suggestion;", "equals", "", "other", "hashCode", "toString", "app_debug"})
final class Suggestion {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String ticker = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String assetKind = null;
    private final double price = 0.0;
    private final double score = 0.0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double dy = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double bazin = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double graham = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double marginBazin = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double marginGraham = null;
    private final double currentWeight = 0.0;
    private final double afterWeight = 0.0;
    private final int sharesAffordable = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sector = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String corrHint = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double annualIncome = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> reasons = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double dpsUsed = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String bazinNote = null;
    
    public Suggestion(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String assetKind, double price, double score, @org.jetbrains.annotations.Nullable()
    java.lang.Double dy, @org.jetbrains.annotations.Nullable()
    java.lang.Double bazin, @org.jetbrains.annotations.Nullable()
    java.lang.Double graham, @org.jetbrains.annotations.Nullable()
    java.lang.Double marginBazin, @org.jetbrains.annotations.Nullable()
    java.lang.Double marginGraham, double currentWeight, double afterWeight, int sharesAffordable, @org.jetbrains.annotations.NotNull()
    java.lang.String sector, @org.jetbrains.annotations.NotNull()
    java.lang.String corrHint, @org.jetbrains.annotations.Nullable()
    java.lang.Double annualIncome, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> reasons, @org.jetbrains.annotations.Nullable()
    java.lang.Double dpsUsed, @org.jetbrains.annotations.Nullable()
    java.lang.String bazinNote) {
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
    public final java.lang.String getAssetKind() {
        return null;
    }
    
    public final double getPrice() {
        return 0.0;
    }
    
    public final double getScore() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getDy() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getBazin() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getGraham() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMarginBazin() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMarginGraham() {
        return null;
    }
    
    public final double getCurrentWeight() {
        return 0.0;
    }
    
    public final double getAfterWeight() {
        return 0.0;
    }
    
    public final int getSharesAffordable() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSector() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCorrHint() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getAnnualIncome() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getReasons() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getDpsUsed() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBazinNote() {
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
    
    public final double component11() {
        return 0.0;
    }
    
    public final double component12() {
        return 0.0;
    }
    
    public final int component13() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component17() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component18() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component19() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
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
    public final com.b3insights.app.ui.screens.tools.Suggestion copy(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String assetKind, double price, double score, @org.jetbrains.annotations.Nullable()
    java.lang.Double dy, @org.jetbrains.annotations.Nullable()
    java.lang.Double bazin, @org.jetbrains.annotations.Nullable()
    java.lang.Double graham, @org.jetbrains.annotations.Nullable()
    java.lang.Double marginBazin, @org.jetbrains.annotations.Nullable()
    java.lang.Double marginGraham, double currentWeight, double afterWeight, int sharesAffordable, @org.jetbrains.annotations.NotNull()
    java.lang.String sector, @org.jetbrains.annotations.NotNull()
    java.lang.String corrHint, @org.jetbrains.annotations.Nullable()
    java.lang.Double annualIncome, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> reasons, @org.jetbrains.annotations.Nullable()
    java.lang.Double dpsUsed, @org.jetbrains.annotations.Nullable()
    java.lang.String bazinNote) {
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