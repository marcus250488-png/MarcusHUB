package com.b3insights.app.util;

/**
 * Cálculo único da carteira (auditoria: evitar 6 cópias divergentes).
 * Método de custo médio: vendas reduzem quantidade e custo proporcionalmente.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0002\u0010\u0011B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004J8\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00042\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b2\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u00a8\u0006\u0012"}, d2 = {"Lcom/b3insights/app/util/PortfolioCalculator;", "", "()V", "aggregate", "", "Lcom/b3insights/app/util/PortfolioCalculator$LotResult;", "positions", "Lcom/b3insights/app/data/local/entity/PortfolioPositionEntity;", "xirrCashFlows", "Lcom/b3insights/app/util/PortfolioReturnCalculator$CashFlow;", "currentValueByTicker", "", "", "", "nowMs", "", "FxQuote", "LotResult", "app_debug"})
public final class PortfolioCalculator {
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.util.PortfolioCalculator INSTANCE = null;
    
    private PortfolioCalculator() {
        super();
    }
    
    /**
     * Agrupa por ticker e aplica compras/vendas em ordem de data.
     * Posições com qty residual <= 0 são omitidas.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.util.PortfolioCalculator.LotResult> aggregate(@org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> positions) {
        return null;
    }
    
    /**
     * Fluxos XIRR: compras negativas, vendas positivas (qty×preço), valor atual positivo no fim.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.b3insights.app.util.PortfolioReturnCalculator.CashFlow> xirrCashFlows(@org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> positions, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Double> currentValueByTicker, long nowMs) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000b\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0013"}, d2 = {"Lcom/b3insights/app/util/PortfolioCalculator$FxQuote;", "", "rate", "", "isFallback", "", "(DZ)V", "()Z", "getRate", "()D", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class FxQuote {
        private final double rate = 0.0;
        private final boolean isFallback = false;
        
        public FxQuote(double rate, boolean isFallback) {
            super();
        }
        
        public final double getRate() {
            return 0.0;
        }
        
        public final boolean isFallback() {
            return false;
        }
        
        public final double component1() {
            return 0.0;
        }
        
        public final boolean component2() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.PortfolioCalculator.FxQuote copy(double rate, boolean isFallback) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0006H\u00c6\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0003JK\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00062\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020!H\u00d6\u0001J\t\u0010\"\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000e\u00a8\u0006#"}, d2 = {"Lcom/b3insights/app/util/PortfolioCalculator$LotResult;", "", "ticker", "", "assetType", "quantity", "", "averagePrice", "invested", "positions", "", "Lcom/b3insights/app/data/local/entity/PortfolioPositionEntity;", "(Ljava/lang/String;Ljava/lang/String;DDDLjava/util/List;)V", "getAssetType", "()Ljava/lang/String;", "getAveragePrice", "()D", "getInvested", "getPositions", "()Ljava/util/List;", "getQuantity", "getTicker", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class LotResult {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String ticker = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String assetType = null;
        private final double quantity = 0.0;
        private final double averagePrice = 0.0;
        
        /**
         * Custo remanescente das compras ainda em carteira (após vendas).
         */
        private final double invested = 0.0;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> positions = null;
        
        public LotResult(@org.jetbrains.annotations.NotNull()
        java.lang.String ticker, @org.jetbrains.annotations.NotNull()
        java.lang.String assetType, double quantity, double averagePrice, double invested, @org.jetbrains.annotations.NotNull()
        java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> positions) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getTicker() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getAssetType() {
            return null;
        }
        
        public final double getQuantity() {
            return 0.0;
        }
        
        public final double getAveragePrice() {
            return 0.0;
        }
        
        /**
         * Custo remanescente das compras ainda em carteira (após vendas).
         */
        public final double getInvested() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> getPositions() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        public final double component3() {
            return 0.0;
        }
        
        public final double component4() {
            return 0.0;
        }
        
        public final double component5() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.PortfolioCalculator.LotResult copy(@org.jetbrains.annotations.NotNull()
        java.lang.String ticker, @org.jetbrains.annotations.NotNull()
        java.lang.String assetType, double quantity, double averagePrice, double invested, @org.jetbrains.annotations.NotNull()
        java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> positions) {
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