package com.b3insights.app.data.model;

/**
 * Página completa de um ativo — agrega tudo que as abas de detalhe precisam exibir.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bs\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\t\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\t\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\t\u0012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\t\u00a2\u0006\u0002\u0010\u0015J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010%\u001a\u00020\u0007H\u00c6\u0003J\u000f\u0010&\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\u000f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\f0\tH\u00c6\u0003J\u000f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u000e0\tH\u00c6\u0003J\u000f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00100\tH\u00c6\u0003J\u000f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00120\tH\u00c6\u0003J\u000f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00140\tH\u00c6\u0003J\u0089\u0001\u0010,\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\t2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\t2\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\t2\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\tH\u00c6\u0001J\u0013\u0010-\u001a\u00020.2\b\u0010/\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00100\u001a\u000201H\u00d6\u0001J\t\u00102\u001a\u00020\u0005H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0019R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\t\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0019R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0019\u00a8\u00063"}, d2 = {"Lcom/b3insights/app/data/model/AssetDetail;", "", "quote", "Lcom/b3insights/app/data/model/AssetQuote;", "about", "", "indicators", "Lcom/b3insights/app/data/model/AssetIndicators;", "dividends", "", "Lcom/b3insights/app/data/model/DividendEntry;", "balanceSheet", "Lcom/b3insights/app/data/model/BalanceSheetEntry;", "results", "Lcom/b3insights/app/data/model/ResultsEntry;", "news", "Lcom/b3insights/app/data/model/NewsItem;", "reports", "Lcom/b3insights/app/data/model/ReportItem;", "priceHistory", "Lcom/b3insights/app/data/model/PricePoint;", "(Lcom/b3insights/app/data/model/AssetQuote;Ljava/lang/String;Lcom/b3insights/app/data/model/AssetIndicators;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getAbout", "()Ljava/lang/String;", "getBalanceSheet", "()Ljava/util/List;", "getDividends", "getIndicators", "()Lcom/b3insights/app/data/model/AssetIndicators;", "getNews", "getPriceHistory", "getQuote", "()Lcom/b3insights/app/data/model/AssetQuote;", "getReports", "getResults", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class AssetDetail {
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.model.AssetQuote quote = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String about = null;
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.model.AssetIndicators indicators = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.b3insights.app.data.model.DividendEntry> dividends = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.b3insights.app.data.model.BalanceSheetEntry> balanceSheet = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.b3insights.app.data.model.ResultsEntry> results = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.b3insights.app.data.model.NewsItem> news = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.b3insights.app.data.model.ReportItem> reports = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.b3insights.app.data.model.PricePoint> priceHistory = null;
    
    public AssetDetail(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.model.AssetQuote quote, @org.jetbrains.annotations.Nullable()
    java.lang.String about, @org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.model.AssetIndicators indicators, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.DividendEntry> dividends, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.BalanceSheetEntry> balanceSheet, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.ResultsEntry> results, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.NewsItem> news, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.ReportItem> reports, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.PricePoint> priceHistory) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.model.AssetQuote getQuote() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAbout() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.model.AssetIndicators getIndicators() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.DividendEntry> getDividends() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.BalanceSheetEntry> getBalanceSheet() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.ResultsEntry> getResults() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.NewsItem> getNews() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.ReportItem> getReports() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.PricePoint> getPriceHistory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.model.AssetQuote component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.model.AssetIndicators component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.DividendEntry> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.BalanceSheetEntry> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.ResultsEntry> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.NewsItem> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.ReportItem> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.PricePoint> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.model.AssetDetail copy(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.model.AssetQuote quote, @org.jetbrains.annotations.Nullable()
    java.lang.String about, @org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.model.AssetIndicators indicators, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.DividendEntry> dividends, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.BalanceSheetEntry> balanceSheet, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.ResultsEntry> results, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.NewsItem> news, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.ReportItem> reports, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.model.PricePoint> priceHistory) {
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