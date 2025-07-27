package com.ahamai.chatapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = iOSSystemBlue,
    onPrimary = iOSBackground,
    primaryContainer = iOSSystemBlue.copy(alpha = 0.1f),
    onPrimaryContainer = iOSSystemBlue,
    secondary = iOSSystemGray,
    onSecondary = iOSBackground,
    secondaryContainer = iOSSystemGray6,
    onSecondaryContainer = iOSLabel,
    tertiary = iOSSystemGray2,
    onTertiary = iOSBackground,
    background = iOSBackground,
    onBackground = iOSLabel,
    surface = iOSBackground,
    onSurface = iOSLabel,
    surfaceVariant = iOSSystemGray6,
    onSurfaceVariant = iOSSecondaryLabel,
    outline = iOSSystemGray4,
    inverseOnSurface = iOSBackground,
    inverseSurface = iOSLabel,
    inversePrimary = iOSSystemBlue.copy(alpha = 0.8f),
    surfaceTint = iOSSystemBlue,
    outlineVariant = iOSSystemGray5,
    scrim = iOSLabel.copy(alpha = 0.5f)
)

private val DarkColorScheme = darkColorScheme(
    primary = iOSSystemBlue,
    onPrimary = iOSBackgroundDark,
    primaryContainer = iOSSystemBlue.copy(alpha = 0.2f),
    onPrimaryContainer = iOSSystemBlue,
    secondary = iOSSystemGray,
    onSecondary = iOSBackgroundDark,
    secondaryContainer = iOSSecondaryBackgroundDark,
    onSecondaryContainer = iOSLabelDark,
    tertiary = iOSSystemGray2,
    onTertiary = iOSBackgroundDark,
    background = iOSBackgroundDark,
    onBackground = iOSLabelDark,
    surface = iOSBackgroundDark,
    onSurface = iOSLabelDark,
    surfaceVariant = iOSSecondaryBackgroundDark,
    onSurfaceVariant = iOSLabelDark.copy(alpha = 0.6f),
    outline = iOSSystemGray,
    inverseOnSurface = iOSBackgroundDark,
    inverseSurface = iOSLabelDark,
    inversePrimary = iOSSystemBlue.copy(alpha = 0.8f),
    surfaceTint = iOSSystemBlue,
    outlineVariant = iOSSystemGray2,
    scrim = iOSLabelDark.copy(alpha = 0.5f)
)

@Composable
fun AhamAITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = iOSTypography,
        content = content
    )
}