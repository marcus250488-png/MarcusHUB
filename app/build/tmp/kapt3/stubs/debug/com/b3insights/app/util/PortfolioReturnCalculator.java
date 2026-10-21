package com.b3insights.app.util;

/**
 * Cálculos de rentabilidade alinhados ao que o mercado (e o Léo / Geração Dividendos) usa:
 *
 * - **TIR (IRR / XIRR)**: ponderada pelo dinheiro — considera datas e valores dos aportes
 *  e dos proventos. Responde: "quanto rendeu o meu dinheiro de fato?"
 *
 * - **TWR (Time-Weighted Return)**: ponderada pelo tempo — remove o efeito do timing
 *  dos aportes. Responde: "como o ativo/carteira performou, independente de eu ter
 *  aportado mais ou menos em determinado momento?"
 *
 * Nunca anualize dividindo o retorno total pelo número de anos (juros simples).
 * Use juros compostos: (1 + R)^(1/anos) − 1
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0002\u001d\u001eB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001d\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0007J;\u0010\b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f\u00a2\u0006\u0002\u0010\u0011J5\u0010\u0012\u001a\u0004\u0018\u00010\u00042\u0018\u0010\u0013\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00040\u00140\f2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\u0002\u0010\u0016J%\u0010\u0017\u001a\u0004\u0018\u00010\u00042\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\b\b\u0002\u0010\u0018\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0019J\u0016\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u000f\u00a8\u0006\u001f"}, d2 = {"Lcom/b3insights/app/util/PortfolioReturnCalculator;", "", "()V", "annualizeCompound", "", "totalReturn", "years", "(DD)Ljava/lang/Double;", "modifiedDietz", "beginValue", "endValue", "flows", "", "Lcom/b3insights/app/util/PortfolioReturnCalculator$CashFlow;", "periodStartMs", "", "periodEndMs", "(DDLjava/util/List;JJ)Ljava/lang/Double;", "twr", "points", "Lkotlin/Pair;", "externalFlows", "(Ljava/util/List;Ljava/util/List;)Ljava/lang/Double;", "xirr", "guess", "(Ljava/util/List;D)Ljava/lang/Double;", "yearsBetween", "startMs", "endMs", "CashFlow", "ReturnResult", "app_debug"})
public final class PortfolioReturnCalculator {
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.util.PortfolioReturnCalculator INSTANCE = null;
    
    private PortfolioReturnCalculator() {
        super();
    }
    
    /**
     * Anualização por juros compostos. totalReturn é decimal (ex.: 0.1495 = 14,95%).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double annualizeCompound(double totalReturn, double years) {
        return null;
    }
    
    /**
     * XIRR / TIR: resolve NPV = 0 para a taxa diária, depois anualiza.
     * Fluxos: aportes negativos, proventos positivos, valor atual positivo no final.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double xirr(@org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.util.PortfolioReturnCalculator.CashFlow> flows, double guess) {
        return null;
    }
    
    /**
     * TWR clássico: encadeia retornos de subperíodos entre fluxos externos.
     * [marks] = valor da carteira logo antes de cada fluxo (exceto o final).
     *
     * Forma prática com lista (date, valueBeforeFlow, externalFlow):
     * HP_i = (V_end_i - V_start_i - CF_i) / (V_start_i)  quando CF no fim do subperíodo
     *
     * Implementação: dada série de (timestamp, portfolioValue) e lista de external CF
     * nos mesmos timestamps, calcula sub-period returns.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double twr(@org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Pair<java.lang.Long, java.lang.Double>> points, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.util.PortfolioReturnCalculator.CashFlow> externalFlows) {
        return null;
    }
    
    /**
     * Modified Dietz — boa aproximação do TWR quando não há mark diário em todo aporte.
     * R = (EMV − BMV − CF) / (BMV + Σ (CF_i × w_i))
     * w_i = proporção do período em que o aporte ficou investido.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double modifiedDietz(double beginValue, double endValue, @org.jetbrains.annotations.NotNull()
    java.util.List<com.b3insights.app.util.PortfolioReturnCalculator.CashFlow> flows, long periodStartMs, long periodEndMs) {
        return null;
    }
    
    public final double yearsBetween(long startMs, long endMs) {
        return 0.0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0015"}, d2 = {"Lcom/b3insights/app/util/PortfolioReturnCalculator$CashFlow;", "", "dateMs", "", "amount", "", "(JD)V", "getAmount", "()D", "getDateMs", "()J", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class CashFlow {
        private final long dateMs = 0L;
        private final double amount = 0.0;
        
        public CashFlow(long dateMs, double amount) {
            super();
        }
        
        public final long getDateMs() {
            return 0L;
        }
        
        public final double getAmount() {
            return 0.0;
        }
        
        public final long component1() {
            return 0L;
        }
        
        public final double component2() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.PortfolioReturnCalculator.CashFlow copy(long dateMs, double amount) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b!\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BW\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010 \u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003Jr\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010#J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\'\u001a\u00020(H\u00d6\u0001J\t\u0010)\u001a\u00020*H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0015\u0010\b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0014\u0010\u0012R\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0015\u0010\u0012R\u0015\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0016\u0010\u0012R\u0015\u0010\t\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0017\u0010\u0012R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000e\u00a8\u0006+"}, d2 = {"Lcom/b3insights/app/util/PortfolioReturnCalculator$ReturnResult;", "", "invested", "", "currentValue", "dividendsTotal", "simplePriceReturnPct", "totalReturnPct", "tirAnnualPct", "twrTotalPct", "twrAnnualPct", "years", "(DDDLjava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;D)V", "getCurrentValue", "()D", "getDividendsTotal", "getInvested", "getSimplePriceReturnPct", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getTirAnnualPct", "getTotalReturnPct", "getTwrAnnualPct", "getTwrTotalPct", "getYears", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(DDDLjava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;D)Lcom/b3insights/app/util/PortfolioReturnCalculator$ReturnResult;", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class ReturnResult {
        private final double invested = 0.0;
        private final double currentValue = 0.0;
        private final double dividendsTotal = 0.0;
        
        /**
         * (atual − investido) / investido — só variação de preço, incompleto
         */
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Double simplePriceReturnPct = null;
        
        /**
         * (atual − investido + proventos) / investido
         */
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Double totalReturnPct = null;
        
        /**
         * TIR anualizada (XIRR), % a.a.
         */
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Double tirAnnualPct = null;
        
        /**
         * TWR acumulado no período, %
         */
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Double twrTotalPct = null;
        
        /**
         * TWR anualizado, % a.a.
         */
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Double twrAnnualPct = null;
        private final double years = 0.0;
        
        public ReturnResult(double invested, double currentValue, double dividendsTotal, @org.jetbrains.annotations.Nullable()
        java.lang.Double simplePriceReturnPct, @org.jetbrains.annotations.Nullable()
        java.lang.Double totalReturnPct, @org.jetbrains.annotations.Nullable()
        java.lang.Double tirAnnualPct, @org.jetbrains.annotations.Nullable()
        java.lang.Double twrTotalPct, @org.jetbrains.annotations.Nullable()
        java.lang.Double twrAnnualPct, double years) {
            super();
        }
        
        public final double getInvested() {
            return 0.0;
        }
        
        public final double getCurrentValue() {
            return 0.0;
        }
        
        public final double getDividendsTotal() {
            return 0.0;
        }
        
        /**
         * (atual − investido) / investido — só variação de preço, incompleto
         */
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Double getSimplePriceReturnPct() {
            return null;
        }
        
        /**
         * (atual − investido + proventos) / investido
         */
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Double getTotalReturnPct() {
            return null;
        }
        
        /**
         * TIR anualizada (XIRR), % a.a.
         */
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Double getTirAnnualPct() {
            return null;
        }
        
        /**
         * TWR acumulado no período, %
         */
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Double getTwrTotalPct() {
            return null;
        }
        
        /**
         * TWR anualizado, % a.a.
         */
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Double getTwrAnnualPct() {
            return null;
        }
        
        public final double getYears() {
            return 0.0;
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
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Double component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Double component5() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Double component6() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Double component7() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Double component8() {
            return null;
        }
        
        public final double component9() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.PortfolioReturnCalculator.ReturnResult copy(double invested, double currentValue, double dividendsTotal, @org.jetbrains.annotations.Nullable()
        java.lang.Double simplePriceReturnPct, @org.jetbrains.annotations.Nullable()
        java.lang.Double totalReturnPct, @org.jetbrains.annotations.Nullable()
        java.lang.Double tirAnnualPct, @org.jetbrains.annotations.Nullable()
        java.lang.Double twrTotalPct, @org.jetbrains.annotations.Nullable()
        java.lang.Double twrAnnualPct, double years) {
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