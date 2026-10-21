package com.b3insights.app.data.remote;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\'\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0089\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\u0010J\u0010\u0010!\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010\"\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010#\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u000b\u0010$\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010%\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010&\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010\'\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010(\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010)\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010*\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010+\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0092\u0001\u0010,\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0007H\u00c6\u0001\u00a2\u0006\u0002\u0010-J\u0013\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00101\u001a\u000202H\u00d6\u0001J\t\u00103\u001a\u00020\u0005H\u00d6\u0001R\u001a\u0010\n\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\f\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0017\u0010\u0012R\u001a\u0010\r\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0018\u0010\u0012R\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0019\u0010\u0012R\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u001c\u0010\u0012R\u001a\u0010\u000b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u001d\u0010\u0012R\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u001e\u0010\u0012R\u001a\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u001f\u0010\u0012R\u001a\u0010\t\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b \u0010\u0012\u00a8\u00064"}, d2 = {"Lcom/b3insights/app/data/remote/BrapiBalanceSheetEntryDto;", "", "endDate", "", "period", "", "totalAssets", "", "totalLiab", "totalStockholderEquity", "cash", "shortLongTermDebt", "longTermDebt", "netDebt", "netReceivables", "propertyPlantEquipment", "(Ljava/lang/Long;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;)V", "getCash", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getEndDate", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getLongTermDebt", "getNetDebt", "getNetReceivables", "getPeriod", "()Ljava/lang/String;", "getPropertyPlantEquipment", "getShortLongTermDebt", "getTotalAssets", "getTotalLiab", "getTotalStockholderEquity", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Long;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;)Lcom/b3insights/app/data/remote/BrapiBalanceSheetEntryDto;", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class BrapiBalanceSheetEntryDto {
    @com.google.gson.annotations.SerializedName(value = "endDate")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long endDate = null;
    @com.google.gson.annotations.SerializedName(value = "period")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String period = null;
    @com.google.gson.annotations.SerializedName(value = "totalAssets")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double totalAssets = null;
    @com.google.gson.annotations.SerializedName(value = "totalLiab")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double totalLiab = null;
    @com.google.gson.annotations.SerializedName(value = "totalStockholderEquity")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double totalStockholderEquity = null;
    @com.google.gson.annotations.SerializedName(value = "cash")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double cash = null;
    @com.google.gson.annotations.SerializedName(value = "shortLongTermDebt")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double shortLongTermDebt = null;
    @com.google.gson.annotations.SerializedName(value = "longTermDebt")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double longTermDebt = null;
    @com.google.gson.annotations.SerializedName(value = "netDebt")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double netDebt = null;
    @com.google.gson.annotations.SerializedName(value = "netReceivables")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double netReceivables = null;
    @com.google.gson.annotations.SerializedName(value = "propertyPlantEquipment")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double propertyPlantEquipment = null;
    
    public BrapiBalanceSheetEntryDto(@org.jetbrains.annotations.Nullable()
    java.lang.Long endDate, @org.jetbrains.annotations.Nullable()
    java.lang.String period, @org.jetbrains.annotations.Nullable()
    java.lang.Double totalAssets, @org.jetbrains.annotations.Nullable()
    java.lang.Double totalLiab, @org.jetbrains.annotations.Nullable()
    java.lang.Double totalStockholderEquity, @org.jetbrains.annotations.Nullable()
    java.lang.Double cash, @org.jetbrains.annotations.Nullable()
    java.lang.Double shortLongTermDebt, @org.jetbrains.annotations.Nullable()
    java.lang.Double longTermDebt, @org.jetbrains.annotations.Nullable()
    java.lang.Double netDebt, @org.jetbrains.annotations.Nullable()
    java.lang.Double netReceivables, @org.jetbrains.annotations.Nullable()
    java.lang.Double propertyPlantEquipment) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getEndDate() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPeriod() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getTotalAssets() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getTotalLiab() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getTotalStockholderEquity() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getCash() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getShortLongTermDebt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getLongTermDebt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getNetDebt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getNetReceivables() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPropertyPlantEquipment() {
        return null;
    }
    
    public BrapiBalanceSheetEntryDto() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.b3insights.app.data.remote.BrapiBalanceSheetEntryDto copy(@org.jetbrains.annotations.Nullable()
    java.lang.Long endDate, @org.jetbrains.annotations.Nullable()
    java.lang.String period, @org.jetbrains.annotations.Nullable()
    java.lang.Double totalAssets, @org.jetbrains.annotations.Nullable()
    java.lang.Double totalLiab, @org.jetbrains.annotations.Nullable()
    java.lang.Double totalStockholderEquity, @org.jetbrains.annotations.Nullable()
    java.lang.Double cash, @org.jetbrains.annotations.Nullable()
    java.lang.Double shortLongTermDebt, @org.jetbrains.annotations.Nullable()
    java.lang.Double longTermDebt, @org.jetbrains.annotations.Nullable()
    java.lang.Double netDebt, @org.jetbrains.annotations.Nullable()
    java.lang.Double netReceivables, @org.jetbrains.annotations.Nullable()
    java.lang.Double propertyPlantEquipment) {
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