package com.b3insights.app.data.remote;

/**
 * Yahoo Finance (não oficial). Usado como fonte padrão de cotação, gráfico e dividendos,
 * porque o plano gratuito da brapi não inclui proventos.
 *
 * Qualquer ticker da B3 usa o sufixo .SA (PETR4.SA, HGLG11.SA).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J@\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u00052\b\b\u0001\u0010\u0007\u001a\u00020\u00052\b\b\u0003\u0010\b\u001a\u00020\u00052\b\b\u0003\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\"\u0010\f\u001a\u00020\r2\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0003\u0010\u000e\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000fJ6\u0010\u0010\u001a\u00020\u00112\b\b\u0001\u0010\u0012\u001a\u00020\u00052\b\b\u0003\u0010\u0013\u001a\u00020\u00142\b\b\u0003\u0010\u0015\u001a\u00020\u00142\b\b\u0003\u0010\u0016\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u0017\u00a8\u0006\u0018"}, d2 = {"Lcom/b3insights/app/data/remote/YahooFinanceService;", "", "getChart", "Lcom/b3insights/app/data/remote/YahooChartResponse;", "symbol", "", "range", "interval", "events", "includePrePost", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getQuoteSummary", "Lcom/b3insights/app/data/remote/YahooQuoteSummaryResponse;", "modules", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "search", "Lcom/b3insights/app/data/remote/YahooSearchResponse;", "query", "quotesCount", "", "newsCount", "enableFuzzyQuery", "(Ljava/lang/String;IIZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface YahooFinanceService {
    
    @retrofit2.http.GET(value = "v8/finance/chart/{symbol}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getChart(@retrofit2.http.Path(value = "symbol")
    @org.jetbrains.annotations.NotNull()
    java.lang.String symbol, @retrofit2.http.Query(value = "range")
    @org.jetbrains.annotations.NotNull()
    java.lang.String range, @retrofit2.http.Query(value = "interval")
    @org.jetbrains.annotations.NotNull()
    java.lang.String interval, @retrofit2.http.Query(value = "events")
    @org.jetbrains.annotations.NotNull()
    java.lang.String events, @retrofit2.http.Query(value = "includePrePost")
    boolean includePrePost, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.remote.YahooChartResponse> $completion);
    
    @retrofit2.http.GET(value = "v1/finance/search")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object search(@retrofit2.http.Query(value = "q")
    @org.jetbrains.annotations.NotNull()
    java.lang.String query, @retrofit2.http.Query(value = "quotesCount")
    int quotesCount, @retrofit2.http.Query(value = "newsCount")
    int newsCount, @retrofit2.http.Query(value = "enableFuzzyQuery")
    boolean enableFuzzyQuery, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.remote.YahooSearchResponse> $completion);
    
    /**
     * Fundamentals / balanço (não oficial).
     */
    @retrofit2.http.GET(value = "v10/finance/quoteSummary/{symbol}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuoteSummary(@retrofit2.http.Path(value = "symbol")
    @org.jetbrains.annotations.NotNull()
    java.lang.String symbol, @retrofit2.http.Query(value = "modules")
    @org.jetbrains.annotations.NotNull()
    java.lang.String modules, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.remote.YahooQuoteSummaryResponse> $completion);
    
    /**
     * Yahoo Finance (não oficial). Usado como fonte padrão de cotação, gráfico e dividendos,
     * porque o plano gratuito da brapi não inclui proventos.
     *
     * Qualquer ticker da B3 usa o sufixo .SA (PETR4.SA, HGLG11.SA).
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}