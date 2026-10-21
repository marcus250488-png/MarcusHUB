package com.b3insights.app.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\u0011\u0010\u0004\u001a\u00020\u0005*\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007\u001a\u0011\u0010\b\u001a\u00020\u0005*\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\n\u001a\u001b\u0010\u000b\u001a\u00020\u0005*\u0004\u0018\u00010\u00062\b\b\u0002\u0010\f\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\r\u001a\u0011\u0010\u000e\u001a\u00020\u0005*\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\n\u001a\u0011\u0010\u000f\u001a\u00020\u0005*\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\n\u001a\u001b\u0010\u0010\u001a\u00020\u0005*\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\u0002\u0010\u0013\u001a\u001b\u0010\u0014\u001a\u00020\u0005*\u0004\u0018\u00010\u00062\b\u0010\u0015\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\r\u001a\u001b\u0010\u0016\u001a\u00020\u0005*\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u00a2\u0006\u0002\u0010\u0019\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0016\u0010\u0002\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"brLocale", "Ljava/util/Locale;", "usLocale", "kotlin.jvm.PlatformType", "asCompactMoney", "", "", "(Ljava/lang/Double;)Ljava/lang/String;", "asCompactVolume", "", "(Ljava/lang/Long;)Ljava/lang/String;", "asCurrency", "currencyCode", "(Ljava/lang/Double;Ljava/lang/String;)Ljava/lang/String;", "asDate", "asDateTime", "asDecimal", "digits", "", "(Ljava/lang/Double;I)Ljava/lang/String;", "asMoneyForTicker", "ticker", "asPercent", "showSign", "", "(Ljava/lang/Double;Z)Ljava/lang/String;", "app_debug"})
public final class FormattersKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Locale brLocale = null;
    private static final java.util.Locale usLocale = null;
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String asCurrency(@org.jetbrains.annotations.Nullable()
    java.lang.Double $this$asCurrency, @org.jetbrains.annotations.NotNull()
    java.lang.String currencyCode) {
        return null;
    }
    
    /**
     * Formata preço conforme o ticker: cripto em USD usa US$; demais em R$.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String asMoneyForTicker(@org.jetbrains.annotations.Nullable()
    java.lang.Double $this$asMoneyForTicker, @org.jetbrains.annotations.Nullable()
    java.lang.String ticker) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String asPercent(@org.jetbrains.annotations.Nullable()
    java.lang.Double $this$asPercent, boolean showSign) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String asDecimal(@org.jetbrains.annotations.Nullable()
    java.lang.Double $this$asDecimal, int digits) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String asCompactVolume(@org.jetbrains.annotations.Nullable()
    java.lang.Long $this$asCompactVolume) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String asCompactMoney(@org.jetbrains.annotations.Nullable()
    java.lang.Double $this$asCompactMoney) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String asDateTime(@org.jetbrains.annotations.Nullable()
    java.lang.Long $this$asDateTime) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String asDate(@org.jetbrains.annotations.Nullable()
    java.lang.Long $this$asDate) {
        return null;
    }
}