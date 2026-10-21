package com.b3insights.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val background: Color,
    val card: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val divider: Color,
    val blueLight: Color,
    val navy: Color,
    val isDark: Boolean
)

val LightAppColors = AppColors(
    background = BackgroundWhite,
    card = SurfaceCard,
    textPrimary = TextPrimary,
    textSecondary = TextSecondary,
    divider = DividerColor,
    blueLight = BlueLight,
    navy = NavyDark,
    isDark = false
)

val DarkAppColors = AppColors(
    background = Color(0xFF0E1116),
    card = Color(0xFF1A1F27),
    textPrimary = Color(0xFFE8ECF2),
    textSecondary = Color(0xFF9AA3B2),
    divider = Color(0xFF2A313C),
    blueLight = Color(0xFF1A2740),
    navy = Color(0xFF8AB4F8),
    isDark = true
)

val LocalAppColors = staticCompositionLocalOf { LightAppColors }

private val LightColors = lightColorScheme(
    primary = BluePrimary,
    onPrimary = Color.White,
    secondary = NavyDark,
    background = BackgroundWhite,
    surface = SurfaceCard,
    surfaceVariant = Color(0xFFEAF1FF),
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    error = RedNegative,
    outline = DividerColor
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF5B8DEF),
    onPrimary = Color.White,
    secondary = Color(0xFF8AB4F8),
    background = Color(0xFF0E1116),
    surface = Color(0xFF1A1F27),
    surfaceVariant = Color(0xFF242A33),
    onBackground = Color(0xFFE8ECF2),
    onSurface = Color(0xFFE8ECF2),
    onSurfaceVariant = Color(0xFF9AA3B2),
    error = Color(0xFFFF6B6B),
    outline = Color(0xFF2A313C)
)

@Composable
fun B3InsightsTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val appColors = if (darkTheme) DarkAppColors else LightAppColors
    CompositionLocalProvider(LocalAppColors provides appColors) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColors else LightColors,
            typography = B3Typography,
            content = content
        )
    }
}
