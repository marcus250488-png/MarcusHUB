package com.b3insights.app.sync;

/**
 * Ponte B3 Insights → Financeiro (Marcus Hub).
 *
 * Cada compra/venda marcada para "descontar no Financeiro" vira um documento em
 *  users/{uid}/b3_hub/aportes/itens/{id}
 * com processado=false. O app Financeiro lê esses itens ao abrir/voltar ao app e cria a
 * transferência Nubank → Banco Inter (Investimento) (compra) ou o contrário (venda),
 * marcando processado=true. O id é gerado aqui e usado como id do documento: publicar
 * duas vezes não duplica.
 *
 * Fila local: se não houver login/internet no momento, o aporte fica guardado em
 * SharedPreferences e é reenviado por [flush] (chamado após o login/sync e ao abrir o app).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0000\n\u0002\u0010$\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u001eB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0016\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0015J\u0016\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u0012J\u0016\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00120\u00192\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u001e\u0010\u001a\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u001b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u001e\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00120\u0019H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/b3insights/app/sync/FinanceiroHub;", "", "()V", "KEY_PENDING", "", "PREFS", "TAG", "WRITE_TIMEOUT_MS", "", "mutex", "Lkotlinx/coroutines/sync/Mutex;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "enqueue", "", "ctx", "Landroid/content/Context;", "a", "Lcom/b3insights/app/sync/FinanceiroHub$Aporte;", "flush", "context", "(Landroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "publicar", "aporte", "readQueue", "", "toMap", "", "writeQueue", "list", "Aporte", "app_debug"})
public final class FinanceiroHub {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "FinanceiroHub";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS = "b3_hub_queue";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_PENDING = "pending";
    private static final long WRITE_TIMEOUT_MS = 20000L;
    @org.jetbrains.annotations.NotNull()
    private static final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlinx.coroutines.sync.Mutex mutex = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.sync.FinanceiroHub INSTANCE = null;
    
    private FinanceiroHub() {
        super();
    }
    
    /**
     * Guarda na fila e tenta enviar em segundo plano (não bloqueia a UI).
     */
    public final void publicar(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.b3insights.app.sync.FinanceiroHub.Aporte aporte) {
    }
    
    /**
     * Envia tudo que estiver pendente. Seguro chamar várias vezes.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object flush(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.util.Map<java.lang.String, java.lang.Object> toMap(com.b3insights.app.sync.FinanceiroHub.Aporte a) {
        return null;
    }
    
    private final void enqueue(android.content.Context ctx, com.b3insights.app.sync.FinanceiroHub.Aporte a) {
    }
    
    private final java.util.List<com.b3insights.app.sync.FinanceiroHub.Aporte> readQueue(android.content.Context ctx) {
        return null;
    }
    
    private final void writeQueue(android.content.Context ctx, java.util.List<com.b3insights.app.sync.FinanceiroHub.Aporte> list) {
    }
    
    /**
     * Um aporte (compra) ou resgate (venda) feito no B3 Insights. Valores em R$.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u001d\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B[\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0007H\u00c6\u0003J\t\u0010#\u001a\u00020\u0007H\u00c6\u0003J\t\u0010$\u001a\u00020\u000bH\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003Jq\u0010\'\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010+\u001a\u00020,H\u00d6\u0001J\t\u0010-\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0011R\u0011\u0010\t\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017\u00a8\u0006."}, d2 = {"Lcom/b3insights/app/sync/FinanceiroHub$Aporte;", "", "ticker", "", "assetType", "side", "quantity", "", "price", "valorBrl", "dataMillis", "", "tags", "notes", "id", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DDDJLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAssetType", "()Ljava/lang/String;", "getDataMillis", "()J", "getId", "getNotes", "getPrice", "()D", "getQuantity", "getSide", "getTags", "getTicker", "getValorBrl", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class Aporte {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String ticker = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String assetType = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String side = null;
        private final double quantity = 0.0;
        private final double price = 0.0;
        private final double valorBrl = 0.0;
        private final long dataMillis = 0L;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String tags = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String notes = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String id = null;
        
        public Aporte(@org.jetbrains.annotations.NotNull()
        java.lang.String ticker, @org.jetbrains.annotations.NotNull()
        java.lang.String assetType, @org.jetbrains.annotations.NotNull()
        java.lang.String side, double quantity, double price, double valorBrl, long dataMillis, @org.jetbrains.annotations.Nullable()
        java.lang.String tags, @org.jetbrains.annotations.Nullable()
        java.lang.String notes, @org.jetbrains.annotations.NotNull()
        java.lang.String id) {
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
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getSide() {
            return null;
        }
        
        public final double getQuantity() {
            return 0.0;
        }
        
        public final double getPrice() {
            return 0.0;
        }
        
        public final double getValorBrl() {
            return 0.0;
        }
        
        public final long getDataMillis() {
            return 0L;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getTags() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getNotes() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component10() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
            return null;
        }
        
        public final double component4() {
            return 0.0;
        }
        
        public final double component5() {
            return 0.0;
        }
        
        public final double component6() {
            return 0.0;
        }
        
        public final long component7() {
            return 0L;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component8() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component9() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.b3insights.app.sync.FinanceiroHub.Aporte copy(@org.jetbrains.annotations.NotNull()
        java.lang.String ticker, @org.jetbrains.annotations.NotNull()
        java.lang.String assetType, @org.jetbrains.annotations.NotNull()
        java.lang.String side, double quantity, double price, double valorBrl, long dataMillis, @org.jetbrains.annotations.Nullable()
        java.lang.String tags, @org.jetbrains.annotations.Nullable()
        java.lang.String notes, @org.jetbrains.annotations.NotNull()
        java.lang.String id) {
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