package com.b3insights.app.util;

/**
 * Graham: √(22,5 × LPA × VPA)
 * Bazin: DPS_anual ÷ 0,06  (yield-alvo de 6%)
 *
 * DPS anual = proventos dos **últimos 12 meses (LTM)**.
 * Preferência:
 * 1) cotação × (Dividend Yield % / 100) quando o DY for confiável
 * 2) soma explícita dos proventos 12m, se for coerente com o DY
 *
 * Nunca usar um provento isolado (último pagamento) como se fosse o total anual —
 * isso gerava tetos absurdos (ex.: R$ 0,30 para ações de R$ 40).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\n\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0005@ABCDB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J7\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\t\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\nJ7\u0010\u000b\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\f\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u000e\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u000fJ-\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0012J9\u0010\u0013\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0015JI\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0019\u001a\u0004\u0018\u00010\u00062\b\u0010\u001a\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u001b\u001a\u00020\u00062\b\b\u0002\u0010\u001c\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u001dJI\u0010\u001e\u001a\u0004\u0018\u00010\u00062\b\u0010\u0018\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0019\u001a\u0004\u0018\u00010\u00062\b\u0010\u001f\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010 \u001a\u00020\u00062\b\b\u0002\u0010!\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\"J!\u0010#\u001a\u0004\u0018\u00010\u00062\b\u0010$\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010%J?\u0010&\u001a\u0004\u0018\u00010\'2\b\u0010\u0018\u001a\u0004\u0018\u00010\u00062\b\u0010(\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0019\u001a\u0004\u0018\u00010\u00062\b\u0010)\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010*J?\u0010+\u001a\u0004\u0018\u00010\u00062\b\u0010\u0018\u001a\u0004\u0018\u00010\u00062\b\u0010(\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0019\u001a\u0004\u0018\u00010\u00062\b\u0010)\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010,JA\u0010-\u001a\u0004\u0018\u00010.2\b\u0010\u0018\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0019\u001a\u0004\u0018\u00010\u00062\b\u0010\u001a\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u00100JA\u00101\u001a\u0004\u0018\u00010\u00062\b\u0010\u0018\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0019\u001a\u0004\u0018\u00010\u00062\b\u00102\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u00103\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010,J\u0019\u00104\u001a\u0004\u0018\u00010\u00062\b\u00105\u001a\u0004\u0018\u00010\u0006H\u0002\u00a2\u0006\u0002\u00106J!\u00107\u001a\u0004\u0018\u00010\u00062\b\u00108\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010%JC\u00109\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020;\u0018\u00010:2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010<\u001a\u0004\u0018\u00010\u00062\b\u0010=\u001a\u0004\u0018\u00010\u00062\b\u0010>\u001a\u0004\u0018\u00010\u0006H\u0002\u00a2\u0006\u0002\u0010?\u00a8\u0006E"}, d2 = {"Lcom/b3insights/app/util/Valuation;", "", "()V", "barsiBreakdown", "Lcom/b3insights/app/util/Valuation$BarsiBreakdown;", "price", "", "dividendYieldPercent", "annualDividendsPerShare", "yieldTargetPercent", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;D)Lcom/b3insights/app/util/Valuation$BarsiBreakdown;", "barsiEstimate", "dy", "annualDps", "yieldTarget", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;D)Ljava/lang/Double;", "bazinBreakdown", "Lcom/b3insights/app/util/Valuation$BazinBreakdown;", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;)Lcom/b3insights/app/util/Valuation$BazinBreakdown;", "bazinCeiling", "lastDividendPerShare", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;)Ljava/lang/Double;", "dcfBreakdown", "Lcom/b3insights/app/util/Valuation$DcfBreakdown;", "lpa", "pl", "profitGrowthPercent", "discountPercent", "terminalGrowthPercent", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;DD)Lcom/b3insights/app/util/Valuation$DcfBreakdown;", "dcfEstimate", "growth", "discount", "terminal", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;DD)Ljava/lang/Double;", "discountVsPrice", "estimate", "(Ljava/lang/Double;Ljava/lang/Double;)Ljava/lang/Double;", "grahamBreakdown", "Lcom/b3insights/app/util/Valuation$GrahamBreakdown;", "vpa", "pvp", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;)Lcom/b3insights/app/util/Valuation$GrahamBreakdown;", "grahamPrice", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;)Ljava/lang/Double;", "lynchBreakdown", "Lcom/b3insights/app/util/Valuation$LynchBreakdown;", "revenueGrowthPercent", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;)Lcom/b3insights/app/util/Valuation$LynchBreakdown;", "lynchEstimate", "profitGrowth", "revenueGrowth", "normalizeYieldPercent", "raw", "(Ljava/lang/Double;)Ljava/lang/Double;", "safetyMargin", "fair", "selectDps", "Lkotlin/Pair;", "", "dpsFromYield", "dpsExplicit", "dyPct", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;)Lkotlin/Pair;", "BarsiBreakdown", "BazinBreakdown", "DcfBreakdown", "GrahamBreakdown", "LynchBreakdown", "app_debug"})
public final class Valuation {
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.util.Valuation INSTANCE = null;
    
    private Valuation() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double grahamPrice(@org.jetbrains.annotations.Nullable()
    java.lang.Double lpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double vpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double price, @org.jetbrains.annotations.Nullable()
    java.lang.Double pl, @org.jetbrains.annotations.Nullable()
    java.lang.Double pvp) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.b3insights.app.util.Valuation.GrahamBreakdown grahamBreakdown(@org.jetbrains.annotations.Nullable()
    java.lang.Double lpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double vpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double price, @org.jetbrains.annotations.Nullable()
    java.lang.Double pl, @org.jetbrains.annotations.Nullable()
    java.lang.Double pvp) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double bazinCeiling(@org.jetbrains.annotations.Nullable()
    java.lang.Double price, @org.jetbrains.annotations.Nullable()
    java.lang.Double dividendYieldPercent, @org.jetbrains.annotations.Nullable()
    java.lang.Double annualDividendsPerShare, @org.jetbrains.annotations.Nullable()
    java.lang.Double lastDividendPerShare) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.b3insights.app.util.Valuation.BazinBreakdown bazinBreakdown(@org.jetbrains.annotations.Nullable()
    java.lang.Double price, @org.jetbrains.annotations.Nullable()
    java.lang.Double dividendYieldPercent, @org.jetbrains.annotations.Nullable()
    java.lang.Double annualDividendsPerShare) {
        return null;
    }
    
    /**
     * Escolhe o DPS anual mais confiável.
     * Se a "soma explícita" for muito menor que cotação×DY (ex.: < 40%),
     * trata como provento isolado / dado sujo e usa o yield.
     */
    private final kotlin.Pair<java.lang.Double, java.lang.String> selectDps(java.lang.Double price, java.lang.Double dpsFromYield, java.lang.Double dpsExplicit, java.lang.Double dyPct) {
        return null;
    }
    
    /**
     * Margem % = (régua − cotação) / régua × 100. Positiva = cotação abaixo da régua.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double safetyMargin(@org.jetbrains.annotations.Nullable()
    java.lang.Double fair, @org.jetbrains.annotations.Nullable()
    java.lang.Double price) {
        return null;
    }
    
    /**
     * Normaliza DY para percentual (ex.: 0,08 → 8%).
     * Descarta valores absurdos (> 80% a.a.) de unidade errada da API.
     */
    private final java.lang.Double normalizeYieldPercent(java.lang.Double raw) {
        return null;
    }
    
    /**
     * Estimativa Lynch: com PEG=1, P/L justo ≈ taxa de crescimento do lucro (%).
     * Preço estimado ≈ LPA × g (g em pontos percentuais, ex.: 12 → P/L 12).
     */
    @org.jetbrains.annotations.Nullable()
    public final com.b3insights.app.util.Valuation.LynchBreakdown lynchBreakdown(@org.jetbrains.annotations.Nullable()
    java.lang.Double lpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double price, @org.jetbrains.annotations.Nullable()
    java.lang.Double pl, @org.jetbrains.annotations.Nullable()
    java.lang.Double profitGrowthPercent, @org.jetbrains.annotations.Nullable()
    java.lang.Double revenueGrowthPercent) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double lynchEstimate(@org.jetbrains.annotations.Nullable()
    java.lang.Double lpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double price, @org.jetbrains.annotations.Nullable()
    java.lang.Double pl, @org.jetbrains.annotations.Nullable()
    java.lang.Double profitGrowth, @org.jetbrains.annotations.Nullable()
    java.lang.Double revenueGrowth) {
        return null;
    }
    
    /**
     * Estimativa de entrada por renda: DPS_LTM ÷ yield-alvo.
     * Yield-alvo padrão 6% (alinhado à lógica de renda; configurável).
     */
    @org.jetbrains.annotations.Nullable()
    public final com.b3insights.app.util.Valuation.BarsiBreakdown barsiBreakdown(@org.jetbrains.annotations.Nullable()
    java.lang.Double price, @org.jetbrains.annotations.Nullable()
    java.lang.Double dividendYieldPercent, @org.jetbrains.annotations.Nullable()
    java.lang.Double annualDividendsPerShare, double yieldTargetPercent) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double barsiEstimate(@org.jetbrains.annotations.Nullable()
    java.lang.Double price, @org.jetbrains.annotations.Nullable()
    java.lang.Double dy, @org.jetbrains.annotations.Nullable()
    java.lang.Double annualDps, double yieldTarget) {
        return null;
    }
    
    /**
     * Valor ≈ CF0×(1+g) / (r−g), com r > g.
     * CF0 padrão = LPA (proxy); g = crescimento do lucro (limitado); r = 10% a.a. default.
     */
    @org.jetbrains.annotations.Nullable()
    public final com.b3insights.app.util.Valuation.DcfBreakdown dcfBreakdown(@org.jetbrains.annotations.Nullable()
    java.lang.Double lpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double price, @org.jetbrains.annotations.Nullable()
    java.lang.Double pl, @org.jetbrains.annotations.Nullable()
    java.lang.Double profitGrowthPercent, double discountPercent, double terminalGrowthPercent) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double dcfEstimate(@org.jetbrains.annotations.Nullable()
    java.lang.Double lpa, @org.jetbrains.annotations.Nullable()
    java.lang.Double price, @org.jetbrains.annotations.Nullable()
    java.lang.Double pl, @org.jetbrains.annotations.Nullable()
    java.lang.Double growth, double discount, double terminal) {
        return null;
    }
    
    /**
     * (estimado − preço) / preço × 100. Positivo = cotação abaixo da estimativa (desconto).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double discountVsPrice(@org.jetbrains.annotations.Nullable()
    java.lang.Double estimate, @org.jetbrains.annotations.Nullable()
    java.lang.Double price) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0007H\u00c6\u0003J;\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b\u00a8\u0006\u001d"}, d2 = {"Lcom/b3insights/app/util/Valuation$BarsiBreakdown;", "", "estimatePrice", "", "dividendsPerShare", "yieldTargetPercent", "formula", "", "note", "(DDDLjava/lang/String;Ljava/lang/String;)V", "getDividendsPerShare", "()D", "getEstimatePrice", "getFormula", "()Ljava/lang/String;", "getNote", "getYieldTargetPercent", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class BarsiBreakdown {
        private final double estimatePrice = 0.0;
        private final double dividendsPerShare = 0.0;
        private final double yieldTargetPercent = 0.0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String formula = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String note = null;
        
        public BarsiBreakdown(double estimatePrice, double dividendsPerShare, double yieldTargetPercent, @org.jetbrains.annotations.NotNull()
        java.lang.String formula, @org.jetbrains.annotations.NotNull()
        java.lang.String note) {
            super();
        }
        
        public final double getEstimatePrice() {
            return 0.0;
        }
        
        public final double getDividendsPerShare() {
            return 0.0;
        }
        
        public final double getYieldTargetPercent() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFormula() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getNote() {
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
        public final com.b3insights.app.util.Valuation.BarsiBreakdown copy(double estimatePrice, double dividendsPerShare, double yieldTargetPercent, @org.jetbrains.annotations.NotNull()
        java.lang.String formula, @org.jetbrains.annotations.NotNull()
        java.lang.String note) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0006H\u00c6\u0003JE\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001J\t\u0010\u001f\u001a\u00020\u0006H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0011\u0010\b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\t\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f\u00a8\u0006 "}, d2 = {"Lcom/b3insights/app/util/Valuation$BazinBreakdown;", "", "ceilingPrice", "", "dividendsPerShare", "dividendPeriod", "", "yieldTargetPercent", "formula", "note", "(DDLjava/lang/String;DLjava/lang/String;Ljava/lang/String;)V", "getCeilingPrice", "()D", "getDividendPeriod", "()Ljava/lang/String;", "getDividendsPerShare", "getFormula", "getNote", "getYieldTargetPercent", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class BazinBreakdown {
        private final double ceilingPrice = 0.0;
        private final double dividendsPerShare = 0.0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String dividendPeriod = null;
        private final double yieldTargetPercent = 0.0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String formula = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String note = null;
        
        public BazinBreakdown(double ceilingPrice, double dividendsPerShare, @org.jetbrains.annotations.NotNull()
        java.lang.String dividendPeriod, double yieldTargetPercent, @org.jetbrains.annotations.NotNull()
        java.lang.String formula, @org.jetbrains.annotations.NotNull()
        java.lang.String note) {
            super();
        }
        
        public final double getCeilingPrice() {
            return 0.0;
        }
        
        public final double getDividendsPerShare() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getDividendPeriod() {
            return null;
        }
        
        public final double getYieldTargetPercent() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFormula() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getNote() {
            return null;
        }
        
        public final double component1() {
            return 0.0;
        }
        
        public final double component2() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
            return null;
        }
        
        public final double component4() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.Valuation.BazinBreakdown copy(double ceilingPrice, double dividendsPerShare, @org.jetbrains.annotations.NotNull()
        java.lang.String dividendPeriod, double yieldTargetPercent, @org.jetbrains.annotations.NotNull()
        java.lang.String formula, @org.jetbrains.annotations.NotNull()
        java.lang.String note) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001b\u001a\u00020\tH\u00c6\u0003JO\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\tH\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020!H\u00d6\u0001J\t\u0010\"\u001a\u00020\tH\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\r\u00a8\u0006#"}, d2 = {"Lcom/b3insights/app/util/Valuation$DcfBreakdown;", "", "estimatePrice", "", "cashFlowPerShare", "growthPercent", "discountPercent", "terminalGrowthPercent", "formula", "", "note", "(DDDDDLjava/lang/String;Ljava/lang/String;)V", "getCashFlowPerShare", "()D", "getDiscountPercent", "getEstimatePrice", "getFormula", "()Ljava/lang/String;", "getGrowthPercent", "getNote", "getTerminalGrowthPercent", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class DcfBreakdown {
        private final double estimatePrice = 0.0;
        private final double cashFlowPerShare = 0.0;
        private final double growthPercent = 0.0;
        private final double discountPercent = 0.0;
        private final double terminalGrowthPercent = 0.0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String formula = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String note = null;
        
        public DcfBreakdown(double estimatePrice, double cashFlowPerShare, double growthPercent, double discountPercent, double terminalGrowthPercent, @org.jetbrains.annotations.NotNull()
        java.lang.String formula, @org.jetbrains.annotations.NotNull()
        java.lang.String note) {
            super();
        }
        
        public final double getEstimatePrice() {
            return 0.0;
        }
        
        public final double getCashFlowPerShare() {
            return 0.0;
        }
        
        public final double getGrowthPercent() {
            return 0.0;
        }
        
        public final double getDiscountPercent() {
            return 0.0;
        }
        
        public final double getTerminalGrowthPercent() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFormula() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getNote() {
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
        
        public final double component4() {
            return 0.0;
        }
        
        public final double component5() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.Valuation.DcfBreakdown copy(double estimatePrice, double cashFlowPerShare, double growthPercent, double discountPercent, double terminalGrowthPercent, @org.jetbrains.annotations.NotNull()
        java.lang.String formula, @org.jetbrains.annotations.NotNull()
        java.lang.String note) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0007H\u00c6\u0003JO\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020!H\u00d6\u0001J\t\u0010\"\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\t\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000f\u00a8\u0006#"}, d2 = {"Lcom/b3insights/app/util/Valuation$GrahamBreakdown;", "", "fairPrice", "", "lpa", "vpa", "lpaSource", "", "vpaSource", "formula", "note", "(DDDLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getFairPrice", "()D", "getFormula", "()Ljava/lang/String;", "getLpa", "getLpaSource", "getNote", "getVpa", "getVpaSource", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class GrahamBreakdown {
        private final double fairPrice = 0.0;
        private final double lpa = 0.0;
        private final double vpa = 0.0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String lpaSource = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String vpaSource = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String formula = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String note = null;
        
        public GrahamBreakdown(double fairPrice, double lpa, double vpa, @org.jetbrains.annotations.NotNull()
        java.lang.String lpaSource, @org.jetbrains.annotations.NotNull()
        java.lang.String vpaSource, @org.jetbrains.annotations.NotNull()
        java.lang.String formula, @org.jetbrains.annotations.NotNull()
        java.lang.String note) {
            super();
        }
        
        public final double getFairPrice() {
            return 0.0;
        }
        
        public final double getLpa() {
            return 0.0;
        }
        
        public final double getVpa() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getLpaSource() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getVpaSource() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFormula() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getNote() {
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
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.Valuation.GrahamBreakdown copy(double fairPrice, double lpa, double vpa, @org.jetbrains.annotations.NotNull()
        java.lang.String lpaSource, @org.jetbrains.annotations.NotNull()
        java.lang.String vpaSource, @org.jetbrains.annotations.NotNull()
        java.lang.String formula, @org.jetbrains.annotations.NotNull()
        java.lang.String note) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0014J\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0014J\t\u0010\u001c\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\tH\u00c6\u0003JX\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\tH\u00c6\u0001\u00a2\u0006\u0002\u0010\u001fJ\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010#\u001a\u00020$H\u00d6\u0001J\t\u0010%\u001a\u00020\tH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014R\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0016\u0010\u0014\u00a8\u0006&"}, d2 = {"Lcom/b3insights/app/util/Valuation$LynchBreakdown;", "", "estimatePrice", "", "lpa", "growthPercent", "peg", "pl", "formula", "", "note", "(DDDLjava/lang/Double;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;)V", "getEstimatePrice", "()D", "getFormula", "()Ljava/lang/String;", "getGrowthPercent", "getLpa", "getNote", "getPeg", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getPl", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "(DDDLjava/lang/Double;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;)Lcom/b3insights/app/util/Valuation$LynchBreakdown;", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class LynchBreakdown {
        private final double estimatePrice = 0.0;
        private final double lpa = 0.0;
        private final double growthPercent = 0.0;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Double peg = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Double pl = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String formula = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String note = null;
        
        public LynchBreakdown(double estimatePrice, double lpa, double growthPercent, @org.jetbrains.annotations.Nullable()
        java.lang.Double peg, @org.jetbrains.annotations.Nullable()
        java.lang.Double pl, @org.jetbrains.annotations.NotNull()
        java.lang.String formula, @org.jetbrains.annotations.NotNull()
        java.lang.String note) {
            super();
        }
        
        public final double getEstimatePrice() {
            return 0.0;
        }
        
        public final double getLpa() {
            return 0.0;
        }
        
        public final double getGrowthPercent() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Double getPeg() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Double getPl() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFormula() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getNote() {
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
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Double component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Double component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.util.Valuation.LynchBreakdown copy(double estimatePrice, double lpa, double growthPercent, @org.jetbrains.annotations.Nullable()
        java.lang.Double peg, @org.jetbrains.annotations.Nullable()
        java.lang.Double pl, @org.jetbrains.annotations.NotNull()
        java.lang.String formula, @org.jetbrains.annotations.NotNull()
        java.lang.String note) {
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