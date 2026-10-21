package com.b3insights.app.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\u001a\"\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00022\u0006\u0010\b\u001a\u00020\u00022\b\u0010\t\u001a\u0004\u0018\u00010\u0002\u001a\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0002\u001a\u000e\u0010\r\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0002\u001a\u000e\u0010\u000e\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u0002\u001a\u001a\u0010\u000f\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u00022\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0002\u001a\u000e\u0010\u0011\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u0002\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"KNOWN_CRYPTO", "", "", "KNOWN_ETFS", "KNOWN_UNITS", "detectAssetType", "Lcom/b3insights/app/data/model/AssetType;", "apiType", "ticker", "name", "isCryptoTicker", "", "raw", "isUsStockTicker", "normalizeB3Ticker", "quoteTickerFor", "assetType", "toYahooSymbol", "app_debug"})
public final class TickersKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Set<java.lang.String> KNOWN_CRYPTO = null;
    
    /**
     * Unidades (B3) terminadas em 11 que NÃO são FII.
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Set<java.lang.String> KNOWN_UNITS = null;
    
    /**
     * ETFs B3 comuns terminados em 11.
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Set<java.lang.String> KNOWN_ETFS = null;
    
    public static final boolean isCryptoTicker(@org.jetbrains.annotations.NotNull()
    java.lang.String raw) {
        return false;
    }
    
    /**
     * Normaliza tickers da B3. Cripto: BTC → BTC-USD para cotação.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String normalizeB3Ticker(@org.jetbrains.annotations.NotNull()
    java.lang.String raw) {
        return null;
    }
    
    /**
     * Símbolo usado só para buscar cotação.
     * BTC (carteira em R$) → BTC-USD no Yahoo; PETR4 → PETR4.SA
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String quoteTickerFor(@org.jetbrains.annotations.NotNull()
    java.lang.String raw, @org.jetbrains.annotations.Nullable()
    java.lang.String assetType) {
        return null;
    }
    
    /**
     * Símbolo no Yahoo Finance.
     */
    public static final boolean isUsStockTicker(@org.jetbrains.annotations.NotNull()
    java.lang.String raw) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String toYahooSymbol(@org.jetbrains.annotations.NotNull()
    java.lang.String raw) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.data.model.AssetType detectAssetType(@org.jetbrains.annotations.Nullable()
    java.lang.String apiType, @org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.Nullable()
    java.lang.String name) {
        return null;
    }
}