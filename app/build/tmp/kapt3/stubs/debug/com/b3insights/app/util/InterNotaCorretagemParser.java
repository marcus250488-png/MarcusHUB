package com.b3insights.app.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001(B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00062\u0006\u0010\u0012\u001a\u00020\u0004H\u0002J\u0017\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0012\u001a\u00020\u0004H\u0002\u00a2\u0006\u0002\u0010\u0015J$\u0010\u0016\u001a\u0016\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u00072\u0006\u0010\u000b\u001a\u00020\u0004H\u0002J$\u0010\u0018\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00072\u0006\u0010\u0019\u001a\u00020\u0004H\u0002J/\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u00060\u001b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u001e\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001f\u0010 J\u001a\u0010!\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\"\u001a\u00020\u00042\u0006\u0010#\u001a\u00020\u0014H\u0002J\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00062\u0006\u0010%\u001a\u00020\u0004J\u0013\u0010&\u001a\u0004\u0018\u00010\u0017*\u00020\u0004H\u0002\u00a2\u0006\u0002\u0010\'R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R&\u0010\u0005\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006)"}, d2 = {"Lcom/b3insights/app/util/InterNotaCorretagemParser;", "", "()V", "TAG", "", "knownAssets", "", "Lkotlin/Triple;", "pdfBoxReady", "", "detectSide", "window", "ensurePdfBox", "", "context", "Landroid/content/Context;", "extractAllQtyPriceTotal", "Lcom/b3insights/app/util/InterNotaCorretagemParser$NumHit;", "text", "extractPregaoDate", "", "(Ljava/lang/String;)Ljava/lang/Long;", "extractQtyPriceTotal", "", "mapTitleInChunk", "chunk", "parse", "Lkotlin/Result;", "Lcom/b3insights/app/util/BrokerageOperation;", "uri", "Landroid/net/Uri;", "parse-gIAlu-s", "(Landroid/content/Context;Landroid/net/Uri;)Ljava/lang/Object;", "parseBlock", "block", "dateMs", "parseText", "raw", "brl", "(Ljava/lang/String;)Ljava/lang/Double;", "NumHit", "app_debug"})
public final class InterNotaCorretagemParser {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "InterNotaParser";
    private static boolean pdfBoxReady = false;
    
    /**
     * Lista (nome na nota → ticker, tipo). Nomes em MAIÚSCULO.
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<kotlin.Triple<java.lang.String, java.lang.String, java.lang.String>> knownAssets = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.util.InterNotaCorretagemParser INSTANCE = null;
    
    private InterNotaCorretagemParser() {
        super();
    }
    
    private final void ensurePdfBox(android.content.Context context) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.util.BrokerageOperation> parseText(@org.jetbrains.annotations.NotNull()
    java.lang.String raw) {
        return null;
    }
    
    private final com.b3insights.app.util.BrokerageOperation parseBlock(java.lang.String block, long dateMs) {
        return null;
    }
    
    /**
     * ticker, assetType, rawName
     */
    private final kotlin.Triple<java.lang.String, java.lang.String, java.lang.String> mapTitleInChunk(java.lang.String chunk) {
        return null;
    }
    
    private final kotlin.Triple<java.lang.Double, java.lang.Double, java.lang.Double> extractQtyPriceTotal(java.lang.String window) {
        return null;
    }
    
    private final java.util.List<com.b3insights.app.util.InterNotaCorretagemParser.NumHit> extractAllQtyPriceTotal(java.lang.String text) {
        return null;
    }
    
    private final java.lang.String detectSide(java.lang.String window) {
        return null;
    }
    
    private final java.lang.Long extractPregaoDate(java.lang.String text) {
        return null;
    }
    
    private final java.lang.Double brl(java.lang.String $this$brl) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0007H\u00c6\u0003J;\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000e\u00a8\u0006\u001d"}, d2 = {"Lcom/b3insights/app/util/InterNotaCorretagemParser$NumHit;", "", "qty", "", "price", "total", "dc", "", "around", "(DDDLjava/lang/String;Ljava/lang/String;)V", "getAround", "()Ljava/lang/String;", "getDc", "getPrice", "()D", "getQty", "getTotal", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    static final class NumHit {
        private final double qty = 0.0;
        private final double price = 0.0;
        private final double total = 0.0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String dc = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String around = null;
        
        public NumHit(double qty, double price, double total, @org.jetbrains.annotations.NotNull()
        java.lang.String dc, @org.jetbrains.annotations.NotNull()
        java.lang.String around) {
            super();
        }
        
        public final double getQty() {
            return 0.0;
        }
        
        public final double getPrice() {
            return 0.0;
        }
        
        public final double getTotal() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getDc() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getAround() {
            return null;
        }
        
        public final double component1() {
            return 0.0;
        }
        
        public final double component2() {
            return 0.0;
        }
        
        public final double component3() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.InterNotaCorretagemParser.NumHit copy(double qty, double price, double total, @org.jetbrains.annotations.NotNull()
        java.lang.String dc, @org.jetbrains.annotations.NotNull()
        java.lang.String around) {
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
}