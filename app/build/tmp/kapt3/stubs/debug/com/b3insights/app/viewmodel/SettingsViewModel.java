package com.b3insights.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0019\u001a\u00020\u001aJ\u0006\u0010\u001b\u001a\u00020\u001aJ\u000e\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010\u001f\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010 \u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010!\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010\"\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001eJ\u0006\u0010#\u001a\u00020\u001aJ\u0006\u0010$\u001a\u00020\u001aR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0013\u0010\n\u001a\u0004\u0018\u00010\u00078F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\r\u001a\u0004\u0018\u00010\u00078F\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\fR\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u00078F\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\fR\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0019\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/b3insights/app/viewmodel/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_settings", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/b3insights/app/data/local/entity/UserSettingsEntity;", "_syncMsg", "", "authManager", "Lcom/b3insights/app/auth/AuthManager;", "currentUserEmail", "getCurrentUserEmail", "()Ljava/lang/String;", "displayName", "getDisplayName", "photoUrl", "getPhotoUrl", "settings", "Lkotlinx/coroutines/flow/StateFlow;", "getSettings", "()Lkotlinx/coroutines/flow/StateFlow;", "syncMsg", "getSyncMsg", "userSettingsDao", "Lcom/b3insights/app/data/local/dao/UserSettingsDao;", "clearSyncMsg", "", "restoreFromCloud", "setAutoUpdate", "enabled", "", "setBiometric", "setDarkTheme", "setNotifications", "setPreferCryptoBrl", "signOut", "syncNow", "app_debug"})
public final class SettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.local.dao.UserSettingsDao userSettingsDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.auth.AuthManager authManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.b3insights.app.data.local.entity.UserSettingsEntity> _settings = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.data.local.entity.UserSettingsEntity> settings = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _syncMsg = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> syncMsg = null;
    
    public SettingsViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.b3insights.app.data.local.entity.UserSettingsEntity> getSettings() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCurrentUserEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDisplayName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPhotoUrl() {
        return null;
    }
    
    public final void setAutoUpdate(boolean enabled) {
    }
    
    public final void setDarkTheme(boolean enabled) {
    }
    
    public final void setPreferCryptoBrl(boolean enabled) {
    }
    
    public final void setNotifications(boolean enabled) {
    }
    
    public final void setBiometric(boolean enabled) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSyncMsg() {
        return null;
    }
    
    public final void syncNow() {
    }
    
    public final void restoreFromCloud() {
    }
    
    public final void clearSyncMsg() {
    }
    
    public final void signOut() {
    }
}