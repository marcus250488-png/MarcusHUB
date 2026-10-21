package com.b3insights.app.data.local.entity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b1\b\u0087\b\u0018\u00002\u00020\u0001B\u00a9\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0015\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0015\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0018J\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\u000eH\u00c6\u0003J\t\u00102\u001a\u00020\u000eH\u00c6\u0003J\t\u00103\u001a\u00020\u000eH\u00c6\u0003J\t\u00104\u001a\u00020\u0005H\u00c6\u0003J\t\u00105\u001a\u00020\u0015H\u00c6\u0003J\t\u00106\u001a\u00020\u0015H\u00c6\u0003J\t\u00107\u001a\u00020\u0005H\u00c6\u0003J\t\u00108\u001a\u00020\u0005H\u00c6\u0003J\t\u00109\u001a\u00020\u0005H\u00c6\u0003J\t\u0010:\u001a\u00020\u0005H\u00c6\u0003J\t\u0010;\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010<\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u0010\u0010=\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010#J\t\u0010>\u001a\u00020\u000eH\u00c6\u0003J\t\u0010?\u001a\u00020\u000eH\u00c6\u0003J\u00b2\u0001\u0010@\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u000e2\b\b\u0002\u0010\u0011\u001a\u00020\u000e2\b\b\u0002\u0010\u0012\u001a\u00020\u000e2\b\b\u0002\u0010\u0013\u001a\u00020\u00052\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00152\b\b\u0002\u0010\u0017\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0002\u0010AJ\u0013\u0010B\u001a\u00020\u00052\b\u0010C\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010D\u001a\u00020\u0003H\u00d6\u0001J\t\u0010E\u001a\u00020\nH\u00d6\u0001R\u0011\u0010\u0017\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001aR\u0013\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\n\n\u0002\u0010$\u001a\u0004\b\"\u0010#R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001aR\u0011\u0010\u0013\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001aR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0011\u0010\u0011\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010(R\u0011\u0010\u0010\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010(R\u0011\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010(R\u0011\u0010\u0012\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010(R\u0011\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0011\u0010\u0016\u001a\u00020\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010.\u00a8\u0006F"}, d2 = {"Lcom/b3insights/app/data/local/entity/UserSettingsEntity;", "", "id", "", "darkTheme", "", "autoUpdateEnabled", "notificationsEnabled", "biometricEnabled", "googleAccountEmail", "", "lastSyncAt", "", "targetAcoesPct", "", "targetFiisPct", "targetEtfsPct", "targetCriptoPct", "targetOutrosPct", "preferCryptoBrl", "targetPatrimonio", "", "targetRendaMensal", "allowEmptyCloudRestore", "(IZZZZLjava/lang/String;Ljava/lang/Long;FFFFFZDDZ)V", "getAllowEmptyCloudRestore", "()Z", "getAutoUpdateEnabled", "getBiometricEnabled", "getDarkTheme", "getGoogleAccountEmail", "()Ljava/lang/String;", "getId", "()I", "getLastSyncAt", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getNotificationsEnabled", "getPreferCryptoBrl", "getTargetAcoesPct", "()F", "getTargetCriptoPct", "getTargetEtfsPct", "getTargetFiisPct", "getTargetOutrosPct", "getTargetPatrimonio", "()D", "getTargetRendaMensal", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(IZZZZLjava/lang/String;Ljava/lang/Long;FFFFFZDDZ)Lcom/b3insights/app/data/local/entity/UserSettingsEntity;", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "user_settings")
public final class UserSettingsEntity {
    @androidx.room.PrimaryKey()
    private final int id = 0;
    private final boolean darkTheme = false;
    private final boolean autoUpdateEnabled = false;
    private final boolean notificationsEnabled = false;
    private final boolean biometricEnabled = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String googleAccountEmail = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long lastSyncAt = null;
    private final float targetAcoesPct = 0.0F;
    private final float targetFiisPct = 0.0F;
    private final float targetEtfsPct = 0.0F;
    private final float targetCriptoPct = 0.0F;
    private final float targetOutrosPct = 0.0F;
    private final boolean preferCryptoBrl = false;
    
    /**
     * Meta de patrimônio em R$ (0 = não definida).
     */
    private final double targetPatrimonio = 0.0;
    
    /**
     * Meta de renda mensal em proventos (R$/mês). 0 = não definida.
     */
    private final double targetRendaMensal = 0.0;
    
    /**
     * Se true, pull com nuvem vazia pode limpar o local (confirmação do usuário).
     */
    private final boolean allowEmptyCloudRestore = false;
    
    public UserSettingsEntity(int id, boolean darkTheme, boolean autoUpdateEnabled, boolean notificationsEnabled, boolean biometricEnabled, @org.jetbrains.annotations.Nullable()
    java.lang.String googleAccountEmail, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastSyncAt, float targetAcoesPct, float targetFiisPct, float targetEtfsPct, float targetCriptoPct, float targetOutrosPct, boolean preferCryptoBrl, double targetPatrimonio, double targetRendaMensal, boolean allowEmptyCloudRestore) {
        super();
    }
    
    public final int getId() {
        return 0;
    }
    
    public final boolean getDarkTheme() {
        return false;
    }
    
    public final boolean getAutoUpdateEnabled() {
        return false;
    }
    
    public final boolean getNotificationsEnabled() {
        return false;
    }
    
    public final boolean getBiometricEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getGoogleAccountEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getLastSyncAt() {
        return null;
    }
    
    public final float getTargetAcoesPct() {
        return 0.0F;
    }
    
    public final float getTargetFiisPct() {
        return 0.0F;
    }
    
    public final float getTargetEtfsPct() {
        return 0.0F;
    }
    
    public final float getTargetCriptoPct() {
        return 0.0F;
    }
    
    public final float getTargetOutrosPct() {
        return 0.0F;
    }
    
    public final boolean getPreferCryptoBrl() {
        return false;
    }
    
    /**
     * Meta de patrimônio em R$ (0 = não definida).
     */
    public final double getTargetPatrimonio() {
        return 0.0;
    }
    
    /**
     * Meta de renda mensal em proventos (R$/mês). 0 = não definida.
     */
    public final double getTargetRendaMensal() {
        return 0.0;
    }
    
    /**
     * Se true, pull com nuvem vazia pode limpar o local (confirmação do usuário).
     */
    public final boolean getAllowEmptyCloudRestore() {
        return false;
    }
    
    public UserSettingsEntity() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final float component10() {
        return 0.0F;
    }
    
    public final float component11() {
        return 0.0F;
    }
    
    public final float component12() {
        return 0.0F;
    }
    
    public final boolean component13() {
        return false;
    }
    
    public final double component14() {
        return 0.0;
    }
    
    public final double component15() {
        return 0.0;
    }
    
    public final boolean component16() {
        return false;
    }
    
    public final boolean component2() {
        return false;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component7() {
        return null;
    }
    
    public final float component8() {
        return 0.0F;
    }
    
    public final float component9() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.local.entity.UserSettingsEntity copy(int id, boolean darkTheme, boolean autoUpdateEnabled, boolean notificationsEnabled, boolean biometricEnabled, @org.jetbrains.annotations.Nullable()
    java.lang.String googleAccountEmail, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastSyncAt, float targetAcoesPct, float targetFiisPct, float targetEtfsPct, float targetCriptoPct, float targetOutrosPct, boolean preferCryptoBrl, double targetPatrimonio, double targetRendaMensal, boolean allowEmptyCloudRestore) {
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