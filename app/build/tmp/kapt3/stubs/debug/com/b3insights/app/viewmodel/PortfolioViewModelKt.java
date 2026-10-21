package com.b3insights.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0002\u00a8\u0006\u0003"}, d2 = {"normalizeDy", "", "raw", "app_debug"})
public final class PortfolioViewModelKt {
    
    /**
     * DY às vezes vem 0.08 (=8%) ou 8.0 (=8%). Normaliza para pontos percentuais.
     */
    private static final double normalizeDy(double raw) {
        return 0.0;
    }
}