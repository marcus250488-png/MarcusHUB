package com.b3insights.app.billing;

/**
 * Acesso completo liberado.
 * Quando integrar Google Play Billing, este objeto volta a validar a compra.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\u000bJ\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0010J\u000e\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0013J\u000e\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0012J\u0006\u0010\u0018\u001a\u00020\u0012R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0019"}, d2 = {"Lcom/b3insights/app/billing/ProManager;", "", "()V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/b3insights/app/billing/ProSubscriptionState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "isEntitled", "", "status", "", "isProUser", "observePro", "Lkotlinx/coroutines/flow/Flow;", "refreshFromCloud", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startCheckout", "context", "Landroid/content/Context;", "startListening", "stopListening", "app_debug"})
public final class ProManager {
    @org.jetbrains.annotations.NotNull()
    private static final kotlinx.coroutines.flow.MutableStateFlow<com.b3insights.app.billing.ProSubscriptionState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.billing.ProSubscriptionState> state = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.billing.ProManager INSTANCE = null;
    
    private ProManager() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.billing.ProSubscriptionState> getState() {
        return null;
    }
    
    public final boolean isEntitled(@org.jetbrains.annotations.NotNull()
    java.lang.String status) {
        return false;
    }
    
    public final boolean isProUser() {
        return false;
    }
    
    public final void startListening() {
    }
    
    public final void stopListening() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object refreshFromCloud(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> observePro() {
        return null;
    }
    
    /**
     * Sem checkout por enquanto (Play Billing no futuro).
     */
    public final void startCheckout(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
}