package com.b3insights.app.ui.screens.ranking;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u001a<\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0007\u001aD\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0007\u001a2\u0010\u0010\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0007\u001a\u001a\u0010\u0013\u001a\u00020\n2\b\u0010\u0014\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0015\u001a\u00020\u0016H\u0002\u001a\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018H\u0002\u00a8\u0006\u001a"}, d2 = {"RankingHubScreen", "", "onCategoryClick", "Lkotlin/Function1;", "Lcom/b3insights/app/ui/screens/ranking/RankingCategory;", "onDividendAgendaClick", "Lkotlin/Function0;", "onNewsClick", "RankingListScreen", "categoryKey", "", "metricKey", "onAssetClick", "onBack", "viewModel", "Lcom/b3insights/app/ui/screens/ranking/RankingListViewModel;", "RankingMetricsScreen", "onMetricClick", "Lcom/b3insights/app/ui/screens/ranking/RankingMetric;", "formatMetric", "metric", "row", "Lcom/b3insights/app/ui/screens/ranking/RankingRow;", "loadHubNews", "", "Lcom/b3insights/app/data/model/NewsItem;", "app_debug"})
public final class RankingScreensKt {
    
    @androidx.compose.runtime.Composable()
    public static final void RankingHubScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.b3insights.app.ui.screens.ranking.RankingCategory, kotlin.Unit> onCategoryClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDividendAgendaClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNewsClick) {
    }
    
    private static final java.util.List<com.b3insights.app.data.model.NewsItem> loadHubNews() {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void RankingMetricsScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String categoryKey, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.b3insights.app.ui.screens.ranking.RankingMetric, kotlin.Unit> onMetricClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void RankingListScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String categoryKey, @org.jetbrains.annotations.NotNull()
    java.lang.String metricKey, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onAssetClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    com.b3insights.app.ui.screens.ranking.RankingListViewModel viewModel) {
    }
    
    private static final java.lang.String formatMetric(com.b3insights.app.ui.screens.ranking.RankingMetric metric, com.b3insights.app.ui.screens.ranking.RankingRow row) {
        return null;
    }
}