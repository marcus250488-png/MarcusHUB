package com.b3insights.app.ui.screens.tools;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000H\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a,\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a6\u0010\b\u001a\u00020\u00012\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0010\u0010\u0011\u001a*\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0014\u0010\u0015\u001a1\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0018\u001a\u00020\u0003H\u0003\u00a2\u0006\u0002\u0010\u0019\u001a,\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00010\u001eH\u0003\u001a,\u0010 \u001a\u00020\u00012\u0006\u0010!\u001a\u00020\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00010\u001e2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00010\u001eH\u0003\u001a4\u0010%\u001a\u00020\u00012\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00010\u001e2\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010!\u001a\u00020\"H\u0007\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\'"}, d2 = {"DiscSlider", "", "label", "", "value", "", "onChange", "Lkotlin/Function1;", "EstCell", "est", "", "disc", "width", "Landroidx/compose/ui/unit/Dp;", "highlight", "", "EstCell-d8LSEHM", "(Ljava/lang/Double;Ljava/lang/Double;FZ)V", "Header", "text", "Header-lG28NQ4", "(Ljava/lang/String;FZ)V", "MethodLine", "name", "note", "(Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/String;)V", "StockValDetailSheet", "row", "Lcom/b3insights/app/ui/screens/tools/StockValRow;", "onDismiss", "Lkotlin/Function0;", "onOpenAsset", "StockValFilterScreen", "vm", "Lcom/b3insights/app/ui/screens/tools/StockValuationViewModel;", "onBack", "onApply", "StockValuationScreen", "onAssetClick", "app_debug"})
public final class StockValuationScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void StockValuationScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onAssetClick, @org.jetbrains.annotations.NotNull()
    com.b3insights.app.ui.screens.tools.StockValuationViewModel vm) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StockValFilterScreen(com.b3insights.app.ui.screens.tools.StockValuationViewModel vm, kotlin.jvm.functions.Function0<kotlin.Unit> onBack, kotlin.jvm.functions.Function0<kotlin.Unit> onApply) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DiscSlider(java.lang.String label, float value, kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onChange) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void StockValDetailSheet(com.b3insights.app.ui.screens.tools.StockValRow row, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenAsset) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MethodLine(java.lang.String name, java.lang.Double est, java.lang.Double disc, java.lang.String note) {
    }
}