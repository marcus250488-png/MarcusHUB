package com.b3insights.app.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\u001a,\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u0006\u0010\t\u001a\u00020\u00012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"BRAPI_ICONS", "", "CRYPTO_ICONS", "FAVICON", "FII_MANAGER_DOMAIN", "", "FMP_IMAGES", "logoCandidates", "", "ticker", "assetType", "apiLogo", "app_debug"})
public final class LogoUrlsKt {
    
    /**
     * Fontes públicas de logos, escolhidas por tipo de ativo (sem token, sem chave no código):
     * - Ações / FIIs / BDRs / ETFs da B3 → ícones da brapi.dev (SVG), pelo ticker
     * - Stocks dos EUA (XEL, MO, TMO…)   → imagens do Financial Modeling Prep, pelo ticker
     * - Criptomoedas                     → set "cryptocurrency-icons" (domínio público) via jsDelivr
     * - FIIs mapeados em FII_MANAGER_DOMAIN → favicon do site da gestora
     *
     * Nenhuma fonte cobre 100% dos ativos: quando todas falham, o AssetLogo mostra as iniciais.
     * Confira os termos de uso de cada fonte antes de publicar o app na Play Store.
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String BRAPI_ICONS = "https://icons.brapi.dev/icons/";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String FMP_IMAGES = "https://financialmodelingprep.com/image-stock/";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CRYPTO_ICONS = "https://cdn.jsdelivr.net/gh/spothq/cryptocurrency-icons@master/128/color/";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String FAVICON = "https://www.google.com/s2/favicons?sz=128&domain=";
    
    /**
     * FIIs → site da gestora (o ícone vem do favicon do site).
     * Motivo: a brapi devolve um quadrado genérico "H" para vários fundos de gestoras diferentes
     * (Kinea, XP, Pátria…) e nada para outros. Para os tickers abaixo o app usa só o ícone da gestora.
     * Fundos que a brapi já traz com logo próprio (BROF11, TRXF11, VGIR11, VILG11) ficam de fora.
     * Para incluir um fundo novo: "TICKER11" to "site-da-gestora.com.br".
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<java.lang.String, java.lang.String> FII_MANAGER_DOMAIN = null;
    
    /**
     * Lista ordenada de URLs candidatas para o logo do ativo.
     * [apiLogo] (ex.: campo logourl da brapi) tem prioridade quando vier em https.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<java.lang.String> logoCandidates(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.Nullable()
    java.lang.String assetType, @org.jetbrains.annotations.Nullable()
    java.lang.String apiLogo) {
        return null;
    }
}