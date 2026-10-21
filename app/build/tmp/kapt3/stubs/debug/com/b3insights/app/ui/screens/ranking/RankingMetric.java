package com.b3insights.app.ui.screens.ranking;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0018\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B/\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u000e\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u0007R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000ej\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b \u00a8\u0006!"}, d2 = {"Lcom/b3insights/app/ui/screens/ranking/RankingMetric;", "", "title", "", "description", "categories", "", "Lcom/b3insights/app/ui/screens/ranking/RankingCategory;", "isProFeature", "", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/util/Set;Z)V", "getCategories", "()Ljava/util/Set;", "getDescription", "()Ljava/lang/String;", "()Z", "getTitle", "requiresProFor", "category", "MAIOR_CAPITALIZACAO", "MAIORES_ALTAS_DIA", "MAIORES_BAIXAS_DIA", "DIVIDEND_YIELD", "MENOR_PL", "MENOR_PVP", "MAIOR_ROE", "MAIOR_MARGEM", "MAIORES_RECEITAS", "MAIORES_LUCROS", "MAIS_BARATAS_GRAHAM", "PRECO_TETO_BAZIN", "VOLUME", "AS_MAIS_QUERIDAS", "app_debug"})
public enum RankingMetric {
    /*public static final*/ MAIOR_CAPITALIZACAO /* = new MAIOR_CAPITALIZACAO(null, null, null, false) */,
    /*public static final*/ MAIORES_ALTAS_DIA /* = new MAIORES_ALTAS_DIA(null, null, null, false) */,
    /*public static final*/ MAIORES_BAIXAS_DIA /* = new MAIORES_BAIXAS_DIA(null, null, null, false) */,
    /*public static final*/ DIVIDEND_YIELD /* = new DIVIDEND_YIELD(null, null, null, false) */,
    /*public static final*/ MENOR_PL /* = new MENOR_PL(null, null, null, false) */,
    /*public static final*/ MENOR_PVP /* = new MENOR_PVP(null, null, null, false) */,
    /*public static final*/ MAIOR_ROE /* = new MAIOR_ROE(null, null, null, false) */,
    /*public static final*/ MAIOR_MARGEM /* = new MAIOR_MARGEM(null, null, null, false) */,
    /*public static final*/ MAIORES_RECEITAS /* = new MAIORES_RECEITAS(null, null, null, false) */,
    /*public static final*/ MAIORES_LUCROS /* = new MAIORES_LUCROS(null, null, null, false) */,
    /*public static final*/ MAIS_BARATAS_GRAHAM /* = new MAIS_BARATAS_GRAHAM(null, null, null, false) */,
    /*public static final*/ PRECO_TETO_BAZIN /* = new PRECO_TETO_BAZIN(null, null, null, false) */,
    /*public static final*/ VOLUME /* = new VOLUME(null, null, null, false) */,
    /*public static final*/ AS_MAIS_QUERIDAS /* = new AS_MAIS_QUERIDAS(null, null, null, false) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String description = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<com.b3insights.app.ui.screens.ranking.RankingCategory> categories = null;
    
    /**
     * Se true, bloqueia FREE com cadeado + modal (quando a categoria também for PRO).
     */
    private final boolean isProFeature = false;
    
    RankingMetric(java.lang.String title, java.lang.String description, java.util.Set<? extends com.b3insights.app.ui.screens.ranking.RankingCategory> categories, boolean isProFeature) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<com.b3insights.app.ui.screens.ranking.RankingCategory> getCategories() {
        return null;
    }
    
    /**
     * Se true, bloqueia FREE com cadeado + modal (quando a categoria também for PRO).
     */
    public final boolean isProFeature() {
        return false;
    }
    
    public final boolean requiresProFor(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.ui.screens.ranking.RankingCategory category) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.b3insights.app.ui.screens.ranking.RankingMetric> getEntries() {
        return null;
    }
}