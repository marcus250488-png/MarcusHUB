package com.b3insights.app.billing;

/**
 * Acesso completo liberado no app.
 * Pagamento (Google Play Billing) será adicionado depois da publicação na Play Store.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/b3insights/app/billing/ProConfig;", "", "()V", "FREE_MAX_ASSETS", "", "OWNER_EMAIL", "", "PLAN", "PRICE_DETAIL", "PRICE_LABEL", "app_debug"})
public final class ProConfig {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PRICE_LABEL = "Gr\u00e1tis";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PRICE_DETAIL = "Todas as fun\u00e7\u00f5es liberadas";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PLAN = "open_access";
    
    /**
     * Sem limite de ativos enquanto não houver Play Billing.
     */
    public static final int FREE_MAX_ASSETS = 2147483647;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String OWNER_EMAIL = "marcus.25.04.88@gmail.com";
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.billing.ProConfig INSTANCE = null;
    
    private ProConfig() {
        super();
    }
}