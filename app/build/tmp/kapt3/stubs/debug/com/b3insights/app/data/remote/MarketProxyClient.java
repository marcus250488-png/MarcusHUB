package com.b3insights.app.data.remote;

/**
 * Chama o backend Firebase (marketQuote / marketSearch) quando MARKET_API_BASE estiver definido.
 * Token da brapi NÃO fica no APK nesse fluxo.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\t\u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/b3insights/app/data/remote/MarketProxyClient;", "", "()V", "client", "Lokhttp3/OkHttpClient;", "base", "", "isEnabled", "", "quoteJson", "ticker", "app_debug"})
public final class MarketProxyClient {
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.OkHttpClient client = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.data.remote.MarketProxyClient INSTANCE = null;
    
    private MarketProxyClient() {
        super();
    }
    
    public final boolean isEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String base() {
        return null;
    }
    
    /**
     * GET marketQuote?ticker=PETR4 — devolve JSON bruto da brapi ou null se desabilitado/erro.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String quoteJson(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker) {
        return null;
    }
}