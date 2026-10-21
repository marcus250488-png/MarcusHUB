package com.b3insights.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0019\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bo\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\tH\u00c6\u0003J\u000f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00020\t0\u0005H\u00c6\u0003J\t\u0010!\u001a\u00020\rH\u00c6\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\tH\u00c6\u0003Js\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\b\u001a\u00020\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\u00052\b\b\u0002\u0010\f\u001a\u00020\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\tH\u00c6\u0001J\u0013\u0010$\u001a\u00020\r2\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010&\u001a\u00020\'H\u00d6\u0001J\t\u0010(\u001a\u00020\tH\u00d6\u0001R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0014R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006)"}, d2 = {"Lcom/b3insights/app/viewmodel/HomeUiState;", "", "summary", "Lcom/b3insights/app/viewmodel/PortfolioSummary;", "myAssets", "", "Lcom/b3insights/app/data/model/AssetQuote;", "highlights", "query", "", "searchResults", "recentSearches", "isSearching", "", "errorMessage", "(Lcom/b3insights/app/viewmodel/PortfolioSummary;Ljava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/util/List;Ljava/util/List;ZLjava/lang/String;)V", "getErrorMessage", "()Ljava/lang/String;", "getHighlights", "()Ljava/util/List;", "()Z", "getMyAssets", "getQuery", "getRecentSearches", "getSearchResults", "getSummary", "()Lcom/b3insights/app/viewmodel/PortfolioSummary;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class HomeUiState {
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.viewmodel.PortfolioSummary summary = null;
    
    /**
     * Ativos da carteira (ordenados por valor); vazio se a carteira estiver vazia.
     */
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.b3insights.app.data.model.AssetQuote> myAssets = null;
    
    /**
     * "Destaques do Momento": cotações dos ativos mais pesquisados.
     */
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.b3insights.app.data.model.AssetQuote> highlights = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String query = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.b3insights.app.data.model.AssetQuote> searchResults = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> recentSearches = null;
    private final boolean isSearching = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    
    public HomeUiState(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.viewmodel.PortfolioSummary summary, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.AssetQuote> myAssets, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.AssetQuote> highlights, @org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.AssetQuote> searchResults, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> recentSearches, boolean isSearching, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.viewmodel.PortfolioSummary getSummary() {
        return null;
    }
    
    /**
     * Ativos da carteira (ordenados por valor); vazio se a carteira estiver vazia.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.AssetQuote> getMyAssets() {
        return null;
    }
    
    /**
     * "Destaques do Momento": cotações dos ativos mais pesquisados.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.AssetQuote> getHighlights() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.AssetQuote> getSearchResults() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getRecentSearches() {
        return null;
    }
    
    public final boolean isSearching() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    public HomeUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.viewmodel.PortfolioSummary component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.AssetQuote> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.AssetQuote> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.AssetQuote> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component6() {
        return null;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.viewmodel.HomeUiState copy(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.viewmodel.PortfolioSummary summary, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.AssetQuote> myAssets, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.AssetQuote> highlights, @org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.AssetQuote> searchResults, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> recentSearches, boolean isSearching, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage) {
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