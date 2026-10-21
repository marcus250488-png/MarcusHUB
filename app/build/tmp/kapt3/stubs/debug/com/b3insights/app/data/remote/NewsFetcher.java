package com.b3insights.app.data.remote;

/**
 * Notícias de mercado via RSS (Google News / InfoMoney).
 * Yahoo Finance search frequentemente retorna news=[] ou 429 no mobile.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u0007J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u0006\u001a\u00020\u0007J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\nH\u0002J\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u000f\u001a\u00020\nH\u0002\u00a8\u0006\u0010"}, d2 = {"Lcom/b3insights/app/data/remote/NewsFetcher;", "", "()V", "fetchMarketNews", "", "Lcom/b3insights/app/data/model/NewsItem;", "limit", "", "fetchNewsForTicker", "ticker", "", "parseDate", "", "raw", "parseRss", "feedUrl", "app_debug"})
public final class NewsFetcher {
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.data.remote.NewsFetcher INSTANCE = null;
    
    private NewsFetcher() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.NewsItem> fetchNewsForTicker(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, int limit) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.data.model.NewsItem> fetchMarketNews(int limit) {
        return null;
    }
    
    private final java.util.List<com.b3insights.app.data.model.NewsItem> parseRss(java.lang.String feedUrl) {
        return null;
    }
    
    private final long parseDate(java.lang.String raw) {
        return 0L;
    }
}