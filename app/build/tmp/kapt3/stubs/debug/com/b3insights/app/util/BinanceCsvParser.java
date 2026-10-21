package com.b3insights.app.util;

/**
 * Parser do CSV "Histórico de Compras com Fiduciária" da Binance (pt-BR).
 *
 * Colunas típicas:
 * Tempo, Método, Valor a gastar, Receber Quantia, Taxa, Preço, Status, ID da Transação
 * 2026-09-10 11:53:03,Saldo,10.00 BRL,0.00002524 BTC,0.00 BRL,396196.51 BTC/BRL,Successful,N0179...
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004H\u0002J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0002J/\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00040\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014J \u0010\u0015\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0017\u0012\u0006\u0012\u0004\u0018\u00010\t0\u00162\u0006\u0010\u0018\u001a\u00020\tH\u0002J\u0017\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u0018\u001a\u00020\tH\u0002\u00a2\u0006\u0002\u0010\u001bJ \u0010\u001c\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0017\u0012\u0006\u0012\u0004\u0018\u00010\t0\u00162\u0006\u0010\u0018\u001a\u00020\tH\u0002J&\u0010\u001d\u001a\u0004\u0018\u00010\u000e2\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00042\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\t0\u0004H\u0002J\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00042\u0006\u0010\u0018\u001a\u00020\tJ\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020\t0\u00042\u0006\u0010!\u001a\u00020\tH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\""}, d2 = {"Lcom/b3insights/app/util/BinanceCsvParser;", "", "()V", "dateFormats", "", "Ljava/text/SimpleDateFormat;", "looksLikeHeader", "", "cols", "", "normalizeCryptoTicker", "symbol", "parse", "Lkotlin/Result;", "Lcom/b3insights/app/util/BrokerageOperation;", "context", "Landroid/content/Context;", "uri", "Landroid/net/Uri;", "parse-gIAlu-s", "(Landroid/content/Context;Landroid/net/Uri;)Ljava/lang/Object;", "parseAmountAsset", "Lkotlin/Pair;", "", "raw", "parseDate", "", "(Ljava/lang/String;)Ljava/lang/Long;", "parsePrice", "parseRow", "header", "parseText", "splitCsvLine", "line", "app_debug"})
public final class BinanceCsvParser {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.text.SimpleDateFormat> dateFormats = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.util.BinanceCsvParser INSTANCE = null;
    
    private BinanceCsvParser() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.util.BrokerageOperation> parseText(@org.jetbrains.annotations.NotNull()
    java.lang.String raw) {
        return null;
    }
    
    private final boolean looksLikeHeader(java.util.List<java.lang.String> cols) {
        return false;
    }
    
    private final com.b3insights.app.util.BrokerageOperation parseRow(java.util.List<java.lang.String> cols, java.util.List<java.lang.String> header) {
        return null;
    }
    
    /**
     * "0.00002524 BTC" → qty + symbol
     */
    private final kotlin.Pair<java.lang.Double, java.lang.String> parseAmountAsset(java.lang.String raw) {
        return null;
    }
    
    /**
     * "396196.51 BTC/BRL" → price + pair
     */
    private final kotlin.Pair<java.lang.Double, java.lang.String> parsePrice(java.lang.String raw) {
        return null;
    }
    
    private final java.lang.Long parseDate(java.lang.String raw) {
        return null;
    }
    
    /**
     * Guarda ticker "BTC", "ETH"… (sem -USD) para o preço médio em R$
     * não ser multiplicado de novo pelo câmbio na carteira.
     * A cotação atual ainda resolve BTC → BTC-USD no repositório.
     */
    private final java.lang.String normalizeCryptoTicker(java.lang.String symbol) {
        return null;
    }
    
    private final java.util.List<java.lang.String> splitCsvLine(java.lang.String line) {
        return null;
    }
}