package com.b3insights.app.data.recommended;

/**
 * Score 0–100 determinístico a partir de indicadores disponíveis. Não inventa campos.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002Ju\u0010\u0003\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u00042\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\u0002\u0010\u0015JI\u0010\u0016\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u00042\b\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002\u00a2\u0006\u0002\u0010\u0017J?\u0010\u0018\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u00042\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002\u00a2\u0006\u0002\u0010\u0019Jo\u0010\u001a\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u00042\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u000b2\b\u0010\u001b\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002\u00a2\u0006\u0002\u0010\u001cJ*\u0010\u001d\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u00042\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002\u00a8\u0006\u001f"}, d2 = {"Lcom/b3insights/app/data/recommended/PortfolioScoring;", "", "()V", "score", "Lkotlin/Pair;", "", "", "Lcom/b3insights/app/data/recommended/FactorNote;", "category", "", "dy", "", "pl", "pvp", "roe", "netMargin", "debtToEbitda", "volume", "", "hasQuote", "", "(Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Long;Z)Lkotlin/Pair;", "scoreBdr", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Long;)Lkotlin/Pair;", "scoreFii", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Long;)Lkotlin/Pair;", "scoreStock", "debt", "(Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Long;)Lkotlin/Pair;", "weightedScore", "factors", "app_debug"})
public final class PortfolioScoring {
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.data.recommended.PortfolioScoring INSTANCE = null;
    
    private PortfolioScoring() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.Pair<java.lang.Integer, java.util.List<com.b3insights.app.data.recommended.FactorNote>> score(@org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.Nullable()
    java.lang.Double dy, @org.jetbrains.annotations.Nullable()
    java.lang.Double pl, @org.jetbrains.annotations.Nullable()
    java.lang.Double pvp, @org.jetbrains.annotations.Nullable()
    java.lang.Double roe, @org.jetbrains.annotations.Nullable()
    java.lang.Double netMargin, @org.jetbrains.annotations.Nullable()
    java.lang.Double debtToEbitda, @org.jetbrains.annotations.Nullable()
    java.lang.Long volume, boolean hasQuote) {
        return null;
    }
    
    private final kotlin.Pair<java.lang.Integer, java.util.List<com.b3insights.app.data.recommended.FactorNote>> scoreFii(java.lang.Double dy, java.lang.Double pvp, java.lang.Long volume) {
        return null;
    }
    
    private final kotlin.Pair<java.lang.Integer, java.util.List<com.b3insights.app.data.recommended.FactorNote>> scoreBdr(java.lang.Double pl, java.lang.Double pvp, java.lang.Double roe, java.lang.Long volume) {
        return null;
    }
    
    private final kotlin.Pair<java.lang.Integer, java.util.List<com.b3insights.app.data.recommended.FactorNote>> scoreStock(java.lang.String category, java.lang.Double dy, java.lang.Double pl, java.lang.Double pvp, java.lang.Double roe, java.lang.Double netMargin, java.lang.Double debt, java.lang.Long volume) {
        return null;
    }
    
    /**
     * Só pontua fatores com nota; redistribui pesos entre os disponíveis. Score nulo se < 40% dos pesos tiverem dado.
     */
    private final kotlin.Pair<java.lang.Integer, java.util.List<com.b3insights.app.data.recommended.FactorNote>> weightedScore(java.util.List<com.b3insights.app.data.recommended.FactorNote> factors) {
        return null;
    }
}