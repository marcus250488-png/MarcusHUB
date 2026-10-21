package com.b3insights.app.notifications;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tJ&\u0010\f\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/b3insights/app/notifications/ValuationNotifier;", "", "()V", "CHANNEL_ID", "", "CHANNEL_NAME", "canPost", "", "context", "Landroid/content/Context;", "ensureChannel", "", "show", "id", "", "title", "body", "app_debug"})
public final class ValuationNotifier {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_ID = "valuation_alerts";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_NAME = "Alertas Graham / Bazin";
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.notifications.ValuationNotifier INSTANCE = null;
    
    private ValuationNotifier() {
        super();
    }
    
    public final void ensureChannel(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final boolean canPost(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final void show(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int id, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String body) {
    }
}