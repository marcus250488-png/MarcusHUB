package com.b3insights.app.data.local.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006H\'J\u0010\u0010\b\u001a\u0004\u0018\u00010\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2 = {"Lcom/b3insights/app/data/local/dao/UserSettingsDao;", "", "deleteAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSettings", "Lkotlinx/coroutines/flow/Flow;", "Lcom/b3insights/app/data/local/entity/UserSettingsEntity;", "getSettingsOnce", "upsert", "entity", "(Lcom/b3insights/app/data/local/entity/UserSettingsEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface UserSettingsDao {
    
    @androidx.room.Query(value = "SELECT * FROM user_settings WHERE id = 0 LIMIT 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.b3insights.app.data.local.entity.UserSettingsEntity> getSettings();
    
    @androidx.room.Query(value = "SELECT * FROM user_settings WHERE id = 0 LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSettingsOnce(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.b3insights.app.data.local.entity.UserSettingsEntity> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object upsert(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.local.entity.UserSettingsEntity entity, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM user_settings")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}