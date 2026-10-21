package com.b3insights.app.sync;

/**
 * Isolamento de dados por conta (auditoria C-03).
 * Room ainda é um banco por aparelho, mas no logout e na troca de UID
 * limpamos tabelas de usuário e caches em memória para não misturar contas.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0010\u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\r\u001a\u00020\u000eJ\u001e\u0010\u000f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0004H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0018\u0010\u0014\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0015"}, d2 = {"Lcom/b3insights/app/sync/LocalSessionGuard;", "", "()V", "KEY_UID", "", "PREFS", "TAG", "clearUserLocalData", "Lkotlin/Result;", "", "clearUserLocalData-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "lastUid", "context", "Landroid/content/Context;", "onAuthenticated", "uid", "(Landroid/content/Context;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onSignedOut", "(Landroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "rememberUid", "app_debug"})
public final class LocalSessionGuard {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "LocalSessionGuard";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS = "b3_session";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_UID = "last_uid";
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.sync.LocalSessionGuard INSTANCE = null;
    
    private LocalSessionGuard() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String lastUid(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    public final void rememberUid(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    java.lang.String uid) {
    }
    
    /**
     * Se o UID logado for diferente do anterior, limpa dados locais antes do sync.
     * Evita enviar carteira da conta A para a conta B.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object onAuthenticated(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object onSignedOut(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}