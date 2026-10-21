package com.b3insights.app.sync;

/**
 * Sincroniza carteira, favoritos, alertas, pesquisas e ajustes com Firestore
 * em users/{uid}/...
 *
 * Regras de segurança:
 * - Nunca apaga a nuvem se o local estiver vazio.
 * - No login: prioriza puxar da nuvem; só envia local se a nuvem estiver vazia.
 * - Merge: une posições locais + nuvem quando os dois têm dados.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u000e\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\n\u0010\b\u001a\u0004\u0018\u00010\tH\u0002J \u0010\n\u001a\u0004\u0018\u00010\u000b2\u0014\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0001\u0018\u00010\rH\u0002J$\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0082@\u00a2\u0006\u0002\u0010\u0015J$\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0013H\u0082@\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u000bH\u0002J\u0016\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0082@\u00a2\u0006\u0002\u0010\u001aJ(\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001c2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0016\u0010 \u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0082@\u00a2\u0006\u0002\u0010\u001aJ\u0016\u0010!\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0082@\u00a2\u0006\u0002\u0010\u001aJ\u0016\u0010\"\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0082@\u00a2\u0006\u0002\u0010\u001aJ\u0016\u0010#\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0082@\u00a2\u0006\u0002\u0010\u001aJ\u000e\u0010$\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010%J\u0018\u0010&\u001a\u00020\u000f2\b\b\u0002\u0010\'\u001a\u00020(H\u0086@\u00a2\u0006\u0002\u0010)J&\u0010*\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001c2\b\b\u0002\u0010\'\u001a\u00020(H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b+\u0010)J\u0018\u0010,\u001a\u00020\u000f2\b\b\u0002\u0010\'\u001a\u00020(H\u0086@\u00a2\u0006\u0002\u0010)J\u0018\u0010-\u001a\u00020\u000f2\b\b\u0002\u0010\'\u001a\u00020(H\u0086@\u00a2\u0006\u0002\u0010)J\u0018\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010\'\u001a\u00020(H\u0086@\u00a2\u0006\u0002\u0010)J\u000e\u0010/\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010%J\u001c\u00100\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001cH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b1\u0010%J\u001c\u00102\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001cH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b3\u0010%J\n\u00104\u001a\u0004\u0018\u00010\u0004H\u0002J\u0012\u00105\u001a\u0004\u0018\u00010\u00112\u0006\u00104\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00066"}, d2 = {"Lcom/b3insights/app/sync/CloudSync;", "", "()V", "TAG", "", "alertDocId", "a", "Lcom/b3insights/app/data/local/entity/PriceAlertEntity;", "db", "Lcom/google/firebase/firestore/FirebaseFirestore;", "mapPortfolioDoc", "Lcom/b3insights/app/data/local/entity/PortfolioPositionEntity;", "data", "", "mergeFavorites", "", "root", "Lcom/google/firebase/firestore/DocumentReference;", "local", "", "Lcom/b3insights/app/data/local/entity/FavoriteAssetEntity;", "(Lcom/google/firebase/firestore/DocumentReference;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "mergePortfolio", "portfolioDocId", "p", "pullAlerts", "(Lcom/google/firebase/firestore/DocumentReference;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "pullAll", "Lkotlin/Result;", "userId", "pullAll-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "pullFavorites", "pullPortfolio", "pullRecentSearches", "pullSettings", "pushAfterLocalChange", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "pushAlerts", "allowEmptyOverwrite", "", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "pushAll", "pushAll-gIAlu-s", "pushFavorites", "pushPortfolio", "pushRecentSearches", "pushSettings", "restoreFromCloud", "restoreFromCloud-IoAF18A", "syncOnLogin", "syncOnLogin-IoAF18A", "uid", "userRoot", "app_debug"})
public final class CloudSync {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "CloudSync";
    @org.jetbrains.annotations.NotNull()
    public static final com.b3insights.app.sync.CloudSync INSTANCE = null;
    
    private CloudSync() {
        super();
    }
    
    private final java.lang.String uid() {
        return null;
    }
    
    private final com.google.firebase.firestore.FirebaseFirestore db() {
        return null;
    }
    
    private final com.google.firebase.firestore.DocumentReference userRoot(java.lang.String uid) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object pushSettings(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object pullSettings(com.google.firebase.firestore.DocumentReference root, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object pushPortfolio(boolean allowEmptyOverwrite, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String portfolioDocId(com.b3insights.app.data.local.entity.PortfolioPositionEntity p) {
        return null;
    }
    
    private final java.lang.Object pullPortfolio(com.google.firebase.firestore.DocumentReference root, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object mergePortfolio(com.google.firebase.firestore.DocumentReference root, java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity> local, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final com.b3insights.app.data.local.entity.PortfolioPositionEntity mapPortfolioDoc(java.util.Map<java.lang.String, ? extends java.lang.Object> data) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object pushFavorites(boolean allowEmptyOverwrite, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object pullFavorites(com.google.firebase.firestore.DocumentReference root, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object mergeFavorites(com.google.firebase.firestore.DocumentReference root, java.util.List<com.b3insights.app.data.local.entity.FavoriteAssetEntity> local, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object pushAlerts(boolean allowEmptyOverwrite, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String alertDocId(com.b3insights.app.data.local.entity.PriceAlertEntity a) {
        return null;
    }
    
    private final java.lang.Object pullAlerts(com.google.firebase.firestore.DocumentReference root, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object pushRecentSearches(boolean allowEmptyOverwrite, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object pullRecentSearches(com.google.firebase.firestore.DocumentReference root, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Envia alterações locais (nunca com overwrite vazio).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object pushAfterLocalChange(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}