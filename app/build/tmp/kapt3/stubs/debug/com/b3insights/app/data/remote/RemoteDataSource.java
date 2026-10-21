package com.b3insights.app.data.remote;

/**
 * Fonte de dados:
 * - Yahoo Finance = padrão (cotação, gráfico, dividendos de qualquer ticker da B3)
 * - brapi.dev     = busca de tickers + perfil/P/L/logo (token no local.properties)
 *
 * O plano gratuito da brapi responde 403 se pedirmos dividendos — por isso
 * proventos NUNCA são pedidos lá.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00c0\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\'\u0010\u0018\u001a\u0004\u0018\u00010\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0019H\u0002\u00a2\u0006\u0002\u0010\u001eJ\u0010\u0010\u001f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u000fH\u0002J$\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u001b0\"2\b\b\u0002\u0010#\u001a\u00020$H\u0086@\u00a2\u0006\u0002\u0010%J$\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0\"2\u0006\u0010(\u001a\u00020\r2\u0006\u0010)\u001a\u00020*H\u0086@\u00a2\u0006\u0002\u0010+J*\u0010,\u001a\b\u0012\u0004\u0012\u00020-0\u001b2\u0006\u0010.\u001a\u00020\r2\b\b\u0002\u0010/\u001a\u00020$2\b\b\u0002\u0010#\u001a\u00020$H\u0002J\u0012\u00100\u001a\u0004\u0018\u00010\u00102\u0006\u0010.\u001a\u00020\rH\u0002J&\u00101\u001a\b\u0012\u0004\u0012\u00020\u00100\"2\u0006\u0010(\u001a\u00020\r2\b\b\u0002\u00102\u001a\u000203H\u0086@\u00a2\u0006\u0002\u00104J\u0018\u00105\u001a\b\u0012\u0004\u0012\u0002060\u001b2\b\u00107\u001a\u0004\u0018\u000108H\u0002J\u0018\u00109\u001a\b\u0012\u0004\u0012\u00020:0\u001b2\b\u00107\u001a\u0004\u0018\u00010;H\u0002J.\u0010<\u001a\u00020\u00102\u0006\u0010(\u001a\u00020\r2\b\u0010=\u001a\u0004\u0018\u00010\u00102\b\u0010>\u001a\u0004\u0018\u00010\u00102\b\u0010?\u001a\u0004\u0018\u00010@H\u0002J\u0019\u0010A\u001a\u0004\u0018\u00010\u000f2\b\u0010B\u001a\u0004\u0018\u00010\rH\u0002\u00a2\u0006\u0002\u0010CJ\u0012\u0010D\u001a\u0004\u0018\u00010\r2\u0006\u0010B\u001a\u00020\rH\u0002J\"\u0010E\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u001b0\"2\u0006\u0010F\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010GJ\f\u0010H\u001a\u000203*\u00020IH\u0002J\f\u0010J\u001a\u00020\u0010*\u00020KH\u0002J\f\u0010J\u001a\u00020\u0010*\u00020LH\u0002J\u0014\u0010J\u001a\u00020\u0010*\u00020M2\u0006\u0010(\u001a\u00020\rH\u0002J\u000e\u0010J\u001a\u0004\u0018\u00010\u0010*\u00020IH\u0002J\f\u0010N\u001a\u00020\r*\u00020*H\u0002J\u001c\u0010O\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b*\u0004\u0018\u00010M2\u0006\u0010P\u001a\u00020QH\u0002J\u0012\u0010R\u001a\b\u0012\u0004\u0012\u00020-0\u001b*\u00020MH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R&\u0010\u000b\u001a\u001a\u0012\u0004\u0012\u00020\r\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0013\u001a\u0004\u0018\u00010\r8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006S"}, d2 = {"Lcom/b3insights/app/data/remote/RemoteDataSource;", "", "()V", "brapiService", "Lcom/b3insights/app/data/remote/BrapiService;", "headerInterceptor", "Lokhttp3/Interceptor;", "loggingInterceptor", "Lokhttp3/logging/HttpLoggingInterceptor;", "okHttpClient", "Lokhttp3/OkHttpClient;", "quoteCache", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lkotlin/Pair;", "", "Lcom/b3insights/app/data/model/AssetQuote;", "quoteCacheTtlMs", "retryInterceptor", "token", "getToken", "()Ljava/lang/String;", "yahooService", "Lcom/b3insights/app/data/remote/YahooFinanceService;", "computeDividendYield", "", "dividends", "", "Lcom/b3insights/app/data/model/DividendEntry;", "price", "(Ljava/util/List;Ljava/lang/Double;)Ljava/lang/Double;", "epochToYearLabel", "epochSec", "fetchAllStocks", "Lcom/b3insights/app/data/model/DataResult;", "limit", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchAssetDetail", "Lcom/b3insights/app/data/model/AssetDetail;", "ticker", "period", "Lcom/b3insights/app/data/model/ChartPeriod;", "(Ljava/lang/String;Lcom/b3insights/app/data/model/ChartPeriod;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchCoinbaseCandles", "Lcom/b3insights/app/data/model/PricePoint;", "product", "granularitySec", "fetchCoinbaseSpot", "fetchQuote", "forceRefresh", "", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "mapBalanceSheet", "Lcom/b3insights/app/data/model/BalanceSheetEntry;", "resp", "Lcom/b3insights/app/data/remote/BrapiBalanceSheetResponse;", "mapIncomeStatement", "Lcom/b3insights/app/data/model/ResultsEntry;", "Lcom/b3insights/app/data/remote/BrapiIncomeStatementResponse;", "mergeQuotes", "yahoo", "brapi", "profile", "Lcom/b3insights/app/data/remote/BrapiProfileData;", "parseIsoToMillis", "raw", "(Ljava/lang/String;)Ljava/lang/Long;", "resolveCryptoQuery", "searchTickers", "query", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isBrazilianListing", "Lcom/b3insights/app/data/remote/YahooSearchQuote;", "toAssetQuote", "Lcom/b3insights/app/data/remote/ListedAssetDto;", "Lcom/b3insights/app/data/remote/QuoteResultDto;", "Lcom/b3insights/app/data/remote/YahooChartResult;", "toBrapiRange", "toDividendEntries", "assetType", "Lcom/b3insights/app/data/model/AssetType;", "toPriceHistory", "app_debug"})
public final class RemoteDataSource {
    
    /**
     * Cache simples de cotações (TTL 60s) para acelerar carteira/rankings.
     */
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, kotlin.Pair<java.lang.Long, com.b3insights.app.data.model.AssetQuote>> quoteCache = null;
    private final long quoteCacheTtlMs = 60000L;
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.logging.HttpLoggingInterceptor loggingInterceptor = null;
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.Interceptor headerInterceptor = null;
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.Interceptor retryInterceptor = null;
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient okHttpClient = null;
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.remote.YahooFinanceService yahooService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.remote.BrapiService brapiService = null;
    
    public RemoteDataSource() {
        super();
    }
    
    private final java.lang.String getToken() {
        return null;
    }
    
    /**
     * Cotação de cripto via API pública Coinbase (exchange-grade).
     * product: BTC-USD, ETH-USD, etc.
     */
    private final com.b3insights.app.data.model.AssetQuote fetchCoinbaseSpot(java.lang.String product) {
        return null;
    }
    
    private final java.util.List<com.b3insights.app.data.model.PricePoint> fetchCoinbaseCandles(java.lang.String product, int granularitySec, int limit) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object fetchQuote(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, boolean forceRefresh, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.model.DataResult<com.b3insights.app.data.model.AssetQuote>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object fetchAssetDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.model.ChartPeriod period, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.model.DataResult<com.b3insights.app.data.model.AssetDetail>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object fetchAllStocks(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.model.DataResult<? extends java.util.List<com.b3insights.app.data.model.AssetQuote>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object searchTickers(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.model.DataResult<? extends java.util.List<com.b3insights.app.data.model.AssetQuote>>> $completion) {
        return null;
    }
    
    private final java.lang.String resolveCryptoQuery(java.lang.String raw) {
        return null;
    }
    
    private final java.lang.String toBrapiRange(com.b3insights.app.data.model.ChartPeriod $this$toBrapiRange) {
        return null;
    }
    
    private final com.b3insights.app.data.model.AssetQuote mergeQuotes(java.lang.String ticker, com.b3insights.app.data.model.AssetQuote yahoo, com.b3insights.app.data.model.AssetQuote brapi, com.b3insights.app.data.remote.BrapiProfileData profile) {
        return null;
    }
    
    private final com.b3insights.app.data.model.AssetQuote toAssetQuote(com.b3insights.app.data.remote.YahooChartResult $this$toAssetQuote, java.lang.String ticker) {
        return null;
    }
    
    private final java.util.List<com.b3insights.app.data.model.PricePoint> toPriceHistory(com.b3insights.app.data.remote.YahooChartResult $this$toPriceHistory) {
        return null;
    }
    
    private final java.util.List<com.b3insights.app.data.model.DividendEntry> toDividendEntries(com.b3insights.app.data.remote.YahooChartResult $this$toDividendEntries, com.b3insights.app.data.model.AssetType assetType) {
        return null;
    }
    
    private final java.lang.Double computeDividendYield(java.util.List<com.b3insights.app.data.model.DividendEntry> dividends, java.lang.Double price) {
        return null;
    }
    
    private final com.b3insights.app.data.model.AssetQuote toAssetQuote(com.b3insights.app.data.remote.QuoteResultDto $this$toAssetQuote) {
        return null;
    }
    
    private final com.b3insights.app.data.model.AssetQuote toAssetQuote(com.b3insights.app.data.remote.ListedAssetDto $this$toAssetQuote) {
        return null;
    }
    
    private final boolean isBrazilianListing(com.b3insights.app.data.remote.YahooSearchQuote $this$isBrazilianListing) {
        return false;
    }
    
    private final com.b3insights.app.data.model.AssetQuote toAssetQuote(com.b3insights.app.data.remote.YahooSearchQuote $this$toAssetQuote) {
        return null;
    }
    
    private final java.lang.Long parseIsoToMillis(java.lang.String raw) {
        return null;
    }
    
    private final java.util.List<com.b3insights.app.data.model.BalanceSheetEntry> mapBalanceSheet(com.b3insights.app.data.remote.BrapiBalanceSheetResponse resp) {
        return null;
    }
    
    private final java.util.List<com.b3insights.app.data.model.ResultsEntry> mapIncomeStatement(com.b3insights.app.data.remote.BrapiIncomeStatementResponse resp) {
        return null;
    }
    
    private final java.lang.String epochToYearLabel(long epochSec) {
        return null;
    }
}