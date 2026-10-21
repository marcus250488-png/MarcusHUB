package com.b3insights.app.auth;

/**
 * Login com Google + Firebase Authentication.
 * Guarda nome e foto do perfil localmente para exibir no app.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u001a\u001a\u0010\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u001bJ\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dJ\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fJ&\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!2\b\u0010#\u001a\u0004\u0018\u00010\u001fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b$\u0010%J\u0006\u0010&\u001a\u00020\'J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\"H\u0002J\u0006\u0010+\u001a\u00020)R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u00068F\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\t\u001a\u0004\u0018\u00010\n8F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\r\u001a\u0004\u0018\u00010\n8F\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\fR\u001d\u0010\u000f\u001a\u0004\u0018\u00010\u00108BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\n8F\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\fR\u0016\u0010\u0017\u001a\n \u0019*\u0004\u0018\u00010\u00180\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006,"}, d2 = {"Lcom/b3insights/app/auth/AuthManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "currentUser", "Lcom/google/firebase/auth/FirebaseUser;", "getCurrentUser", "()Lcom/google/firebase/auth/FirebaseUser;", "displayName", "", "getDisplayName", "()Ljava/lang/String;", "email", "getEmail", "firebaseAuth", "Lcom/google/firebase/auth/FirebaseAuth;", "getFirebaseAuth", "()Lcom/google/firebase/auth/FirebaseAuth;", "firebaseAuth$delegate", "Lkotlin/Lazy;", "photoUrl", "getPhotoUrl", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "consumePendingWelcome", "Lkotlin/Pair;", "getGoogleSignInClient", "Lcom/google/android/gms/auth/api/signin/GoogleSignInClient;", "getSignInIntent", "Landroid/content/Intent;", "handleSignInResult", "Lkotlin/Result;", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;", "data", "handleSignInResult-gIAlu-s", "(Landroid/content/Intent;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hasPendingWelcome", "", "saveProfile", "", "account", "signOut", "app_debug"})
public final class AuthManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy firebaseAuth$delegate = null;
    
    public AuthManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final com.google.firebase.auth.FirebaseAuth getFirebaseAuth() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.firebase.auth.FirebaseUser getCurrentUser() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDisplayName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPhotoUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.android.gms.auth.api.signin.GoogleSignInClient getGoogleSignInClient() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.content.Intent getSignInIntent() {
        return null;
    }
    
    private final void saveProfile(com.google.android.gms.auth.api.signin.GoogleSignInAccount account) {
    }
    
    /**
     * True se este login ainda não recebeu e-mail de boas-vindas.
     */
    @org.jetbrains.annotations.Nullable()
    public final kotlin.Pair<java.lang.String, java.lang.String> consumePendingWelcome() {
        return null;
    }
    
    public final boolean hasPendingWelcome() {
        return false;
    }
    
    public final void signOut() {
    }
}