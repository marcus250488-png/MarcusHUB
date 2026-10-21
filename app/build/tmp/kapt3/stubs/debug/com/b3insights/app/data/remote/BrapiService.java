package com.b3insights.app.data.remote;

/**
 * brapi.dev — busca de tickers da B3, cotação, perfil, balanço e indicadores de FII.
 *
 * IMPORTANTE: o plano Gratuito NÃO inclui dividendos. Pedir dividends=true
 * devolve HTTP 403 e derruba a cotação inteira. Dividendos vêm do Yahoo.
 *
 * Endpoints de balanço / DRE / FII indicators exigem plano pago na maioria dos
 * casos; o código trata falha como lista vazia (fallback silencioso).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u000b\u001a\u00020\f2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006JB\u0010\r\u001a\u00020\u000e2\b\b\u0001\u0010\u000f\u001a\u00020\u00052\b\b\u0003\u0010\u0010\u001a\u00020\u00052\b\b\u0003\u0010\u0011\u001a\u00020\u00052\b\b\u0003\u0010\u0012\u001a\u00020\u00132\n\b\u0003\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0015J\"\u0010\u0016\u001a\u00020\u00172\b\b\u0003\u0010\u0018\u001a\u00020\u00052\b\b\u0003\u0010\u0019\u001a\u00020\u001aH\u00a7@\u00a2\u0006\u0002\u0010\u001bJ\"\u0010\u001c\u001a\u00020\u00172\b\b\u0001\u0010\u001d\u001a\u00020\u00052\b\b\u0003\u0010\u0019\u001a\u00020\u001aH\u00a7@\u00a2\u0006\u0002\u0010\u001b\u00a8\u0006\u001e"}, d2 = {"Lcom/b3insights/app/data/remote/BrapiService;", "", "getBalanceSheet", "Lcom/b3insights/app/data/remote/BrapiBalanceSheetResponse;", "symbols", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFiiIndicators", "Lcom/b3insights/app/data/remote/BrapiFiiIndicatorsResponse;", "getIncomeStatement", "Lcom/b3insights/app/data/remote/BrapiIncomeStatementResponse;", "getProfile", "Lcom/b3insights/app/data/remote/BrapiProfileResponse;", "getQuote", "Lcom/b3insights/app/data/remote/QuoteResponse;", "tickers", "range", "interval", "fundamental", "", "modules", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "listStocks", "Lcom/b3insights/app/data/remote/AssetListResponse;", "type", "limit", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchAssets", "search", "app_debug"})
public abstract interface BrapiService {
    
    @retrofit2.http.GET(value = "api/quote/{tickers}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuote(@retrofit2.http.Path(value = "tickers")
    @org.jetbrains.annotations.NotNull()
    java.lang.String tickers, @retrofit2.http.Query(value = "range")
    @org.jetbrains.annotations.NotNull()
    java.lang.String range, @retrofit2.http.Query(value = "interval")
    @org.jetbrains.annotations.NotNull()
    java.lang.String interval, @retrofit2.http.Query(value = "fundamental")
    boolean fundamental, @retrofit2.http.Query(value = "modules")
    @org.jetbrains.annotations.Nullable()
    java.lang.String modules, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.remote.QuoteResponse> $completion);
    
    @retrofit2.http.GET(value = "api/quote/list")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchAssets(@retrofit2.http.Query(value = "search")
    @org.jetbrains.annotations.NotNull()
    java.lang.String search, @retrofit2.http.Query(value = "limit")
    int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.remote.AssetListResponse> $completion);
    
    /**
     * Lista ampla de ações da B3 (sem filtro de busca).
     */
    @retrofit2.http.GET(value = "api/quote/list")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object listStocks(@retrofit2.http.Query(value = "type")
    @org.jetbrains.annotations.NotNull()
    java.lang.String type, @retrofit2.http.Query(value = "limit")
    int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.remote.AssetListResponse> $completion);
    
    @retrofit2.http.GET(value = "api/v2/stocks/profile")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getProfile(@retrofit2.http.Query(value = "symbols")
    @org.jetbrains.annotations.NotNull()
    java.lang.String symbols, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.remote.BrapiProfileResponse> $completion);
    
    /**
     * Balanço patrimonial (anual). Plano pago; falha silenciosamente no free.
     */
    @retrofit2.http.GET(value = "api/v2/stocks/balance-sheet")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getBalanceSheet(@retrofit2.http.Query(value = "symbols")
    @org.jetbrains.annotations.NotNull()
    java.lang.String symbols, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.remote.BrapiBalanceSheetResponse> $completion);
    
    /**
     * DRE / resultados (anual). Plano pago; falha silenciosamente no free.
     */
    @retrofit2.http.GET(value = "api/v2/stocks/income-statement")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getIncomeStatement(@retrofit2.http.Query(value = "symbols")
    @org.jetbrains.annotations.NotNull()
    java.lang.String symbols, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.remote.BrapiIncomeStatementResponse> $completion);
    
    /**
     * Indicadores de FII (P/VP, DY, patrimônio…). Pro ou sandbox MXRF11/HGLG11.
     */
    @retrofit2.http.GET(value = "api/v2/fii/indicators")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFiiIndicators(@retrofit2.http.Query(value = "symbols")
    @org.jetbrains.annotations.NotNull()
    java.lang.String symbols, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.remote.BrapiFiiIndicatorsResponse> $completion);
    
    /**
     * brapi.dev — busca de tickers da B3, cotação, perfil, balanço e indicadores de FII.
     *
     * IMPORTANTE: o plano Gratuito NÃO inclui dividendos. Pedir dividends=true
     * devolve HTTP 403 e derruba a cotação inteira. Dividendos vêm do Yahoo.
     *
     * Endpoints de balanço / DRE / FII indicators exigem plano pago na maioria dos
     * casos; o código trata falha como lista vazia (fallback silencioso).
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}