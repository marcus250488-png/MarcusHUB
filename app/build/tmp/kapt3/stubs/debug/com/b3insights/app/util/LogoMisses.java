package com.b3insights.app.util;

/**
 * Memória (só da sessão) das URLs que já se mostraram inexistentes/inválidas,
 * para não repetir requisição a cada rolagem de lista.
 * Falhas de rede (offline/timeout) NÃO entram aqui, para tentar de novo depois.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005J\u000e\u0010\u000b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0005RN\u0010\u0003\u001aB\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00070\u0007 \u0006* \u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00070\u0007\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/b3insights/app/util/LogoMisses;", "", "()V", "missing", "Ljava/util/concurrent/ConcurrentHashMap$KeySetView;", "", "kotlin.jvm.PlatformType", "", "add", "", "url", "contains", "app_debug"})
public final class LogoMisses {
    private static final java.util.concurrent.ConcurrentHashMap.KeySetView<java.lang.String, java.lang.Boolean> missing = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.util.LogoMisses INSTANCE = null;
    
    private LogoMisses() {
        super();
    }
    
    public final boolean contains(@org.jetbrains.annotations.NotNull()
    java.lang.String url) {
        return false;
    }
    
    public final void add(@org.jetbrains.annotations.NotNull()
    java.lang.String url) {
    }
}