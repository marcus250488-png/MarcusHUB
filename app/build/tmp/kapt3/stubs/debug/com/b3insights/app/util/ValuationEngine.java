package com.b3insights.app.util;

/**
 * Motor de valuation multimétodo.
 * Documenta fórmula, entradas e status — nunca inventa número.
 *
 * Fontes de entrada esperadas: cotação + indicadores já consolidados
 * (brapi/Yahoo via RemoteDataSource). CVM/Gemini ficam no backend quando deployados.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\r\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0002\u0013\u0014B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002Jw\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\b2\b\u0010\n\u001a\u0004\u0018\u00010\b2\b\u0010\u000b\u001a\u0004\u0018\u00010\b2\b\u0010\f\u001a\u0004\u0018\u00010\b2\b\u0010\r\u001a\u0004\u0018\u00010\b2\b\u0010\u000e\u001a\u0004\u0018\u00010\b2\b\u0010\u000f\u001a\u0004\u0018\u00010\b2\b\u0010\u0010\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\u0011\u001a\u00020\b\u00a2\u0006\u0002\u0010\u0012\u00a8\u0006\u0015"}, d2 = {"Lcom/b3insights/app/util/ValuationEngine;", "", "()V", "compute", "Lcom/b3insights/app/util/ValuationEngine$Snapshot;", "ticker", "", "price", "", "lpa", "vpa", "pl", "pvp", "dividendYieldPercent", "annualDividendsPerShare", "profitGrowthPercent", "revenueGrowthPercent", "bazinRequiredYield", "(Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;D)Lcom/b3insights/app/util/ValuationEngine$Snapshot;", "MethodResult", "Snapshot", "app_debug"})
public final class ValuationEngine {
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.util.ValuationEngine INSTANCE = null;
    
    private ValuationEngine() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.util.ValuationEngine.Snapshot compute(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.Nullable()
    java.lang.Double price, @org.jetbrains.annotations.Nullable()
    java.lang.Double lpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double vpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double pl, @org.jetbrains.annotations.Nullable()
    java.lang.Double pvp, @org.jetbrains.annotations.Nullable()
    java.lang.Double dividendYieldPercent, @org.jetbrains.annotations.Nullable()
    java.lang.Double annualDividendsPerShare, @org.jetbrains.annotations.Nullable()
    java.lang.Double profitGrowthPercent, @org.jetbrains.annotations.Nullable()
    java.lang.Double revenueGrowthPercent, double bazinRequiredYield) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J;\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000e\u00a8\u0006\u001d"}, d2 = {"Lcom/b3insights/app/util/ValuationEngine$MethodResult;", "", "methodId", "", "title", "estimate", "Lcom/b3insights/app/data/model/FieldValue;", "discountPercent", "explanation", "(Ljava/lang/String;Ljava/lang/String;Lcom/b3insights/app/data/model/FieldValue;Lcom/b3insights/app/data/model/FieldValue;Ljava/lang/String;)V", "getDiscountPercent", "()Lcom/b3insights/app/data/model/FieldValue;", "getEstimate", "getExplanation", "()Ljava/lang/String;", "getMethodId", "getTitle", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class MethodResult {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String methodId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String title = null;
        @org.jetbrains.annotations.NotNull()
        private final com.b3insights.app.data.model.FieldValue estimate = null;
        
        /**
         * (estimativa − preço) / preço × 100; positivo = cotação abaixo da estimativa
         */
        @org.jetbrains.annotations.NotNull()
        private final com.b3insights.app.data.model.FieldValue discountPercent = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String explanation = null;
        
        public MethodResult(@org.jetbrains.annotations.NotNull()
        java.lang.String methodId, @org.jetbrains.annotations.NotNull()
        java.lang.String title, @org.jetbrains.annotations.NotNull()
        com.b3insights.app.data.model.FieldValue estimate, @org.jetbrains.annotations.NotNull()
        com.b3insights.app.data.model.FieldValue discountPercent, @org.jetbrains.annotations.NotNull()
        java.lang.String explanation) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getMethodId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getTitle() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.data.model.FieldValue getEstimate() {
            return null;
        }
        
        /**
         * (estimativa − preço) / preço × 100; positivo = cotação abaixo da estimativa
         */
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.data.model.FieldValue getDiscountPercent() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getExplanation() {
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
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.data.model.FieldValue component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.data.model.FieldValue component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.ValuationEngine.MethodResult copy(@org.jetbrains.annotations.NotNull()
        java.lang.String methodId, @org.jetbrains.annotations.NotNull()
        java.lang.String title, @org.jetbrains.annotations.NotNull()
        com.b3insights.app.data.model.FieldValue estimate, @org.jetbrains.annotations.NotNull()
        com.b3insights.app.data.model.FieldValue discountPercent, @org.jetbrains.annotations.NotNull()
        java.lang.String explanation) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\u0006\u0010\u000b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0007H\u00c6\u0003J\t\u0010 \u001a\u00020\u0007H\u00c6\u0003J\t\u0010!\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\"\u001a\u00020\rH\u00c6\u0003JY\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\u00072\b\b\u0002\u0010\f\u001a\u00020\rH\u00c6\u0001J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\'\u001a\u00020(H\u00d6\u0001J\t\u0010)\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0011\u0010\t\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006*"}, d2 = {"Lcom/b3insights/app/util/ValuationEngine$Snapshot;", "", "ticker", "", "price", "Lcom/b3insights/app/data/model/FieldValue;", "graham", "Lcom/b3insights/app/util/ValuationEngine$MethodResult;", "bazin", "lynch", "barsi", "dcf", "computedAt", "", "(Ljava/lang/String;Lcom/b3insights/app/data/model/FieldValue;Lcom/b3insights/app/util/ValuationEngine$MethodResult;Lcom/b3insights/app/util/ValuationEngine$MethodResult;Lcom/b3insights/app/util/ValuationEngine$MethodResult;Lcom/b3insights/app/util/ValuationEngine$MethodResult;Lcom/b3insights/app/util/ValuationEngine$MethodResult;J)V", "getBarsi", "()Lcom/b3insights/app/util/ValuationEngine$MethodResult;", "getBazin", "getComputedAt", "()J", "getDcf", "getGraham", "getLynch", "getPrice", "()Lcom/b3insights/app/data/model/FieldValue;", "getTicker", "()Ljava/lang/String;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class Snapshot {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String ticker = null;
        @org.jetbrains.annotations.NotNull()
        private final com.b3insights.app.data.model.FieldValue price = null;
        @org.jetbrains.annotations.NotNull()
        private final com.b3insights.app.util.ValuationEngine.MethodResult graham = null;
        @org.jetbrains.annotations.NotNull()
        private final com.b3insights.app.util.ValuationEngine.MethodResult bazin = null;
        @org.jetbrains.annotations.NotNull()
        private final com.b3insights.app.util.ValuationEngine.MethodResult lynch = null;
        @org.jetbrains.annotations.NotNull()
        private final com.b3insights.app.util.ValuationEngine.MethodResult barsi = null;
        @org.jetbrains.annotations.NotNull()
        private final com.b3insights.app.util.ValuationEngine.MethodResult dcf = null;
        private final long computedAt = 0L;
        
        public Snapshot(@org.jetbrains.annotations.NotNull()
        java.lang.String ticker, @org.jetbrains.annotations.NotNull()
        com.b3insights.app.data.model.FieldValue price, @org.jetbrains.annotations.NotNull()
        com.b3insights.app.util.ValuationEngine.MethodResult graham, @org.jetbrains.annotations.NotNull()
        com.b3insights.app.util.ValuationEngine.MethodResult bazin, @org.jetbrains.annotations.NotNull()
        com.b3insights.app.util.ValuationEngine.MethodResult lynch, @org.jetbrains.annotations.NotNull()
        com.b3insights.app.util.ValuationEngine.MethodResult barsi, @org.jetbrains.annotations.NotNull()
        com.b3insights.app.util.ValuationEngine.MethodResult dcf, long computedAt) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getTicker() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.data.model.FieldValue getPrice() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.ValuationEngine.MethodResult getGraham() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.ValuationEngine.MethodResult getBazin() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.ValuationEngine.MethodResult getLynch() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.ValuationEngine.MethodResult getBarsi() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.ValuationEngine.MethodResult getDcf() {
            return null;
        }
        
        public final long getComputedAt() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.data.model.FieldValue component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.ValuationEngine.MethodResult component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.ValuationEngine.MethodResult component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.ValuationEngine.MethodResult component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.ValuationEngine.MethodResult component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.ValuationEngine.MethodResult component7() {
            return null;
        }
        
        public final long component8() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.ValuationEngine.Snapshot copy(@org.jetbrains.annotations.NotNull()
        java.lang.String ticker, @org.jetbrains.annotations.NotNull()
        com.b3insights.app.data.model.FieldValue price, @org.jetbrains.annotations.NotNull()
        com.b3insights.app.util.ValuationEngine.MethodResult graham, @org.jetbrains.annotations.NotNull()
        com.b3insights.app.util.ValuationEngine.MethodResult bazin, @org.jetbrains.annotations.NotNull()
        com.b3insights.app.util.ValuationEngine.MethodResult lynch, @org.jetbrains.annotations.NotNull()
        com.b3insights.app.util.ValuationEngine.MethodResult barsi, @org.jetbrains.annotations.NotNull()
        com.b3insights.app.util.ValuationEngine.MethodResult dcf, long computedAt) {
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