package com.example.nexthire.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val NextHireNavy = Color(0xFF102A43)
val NextHireMint = Color(0xFF99F6E4)

private val LightColors = lightColorScheme(
    primary = Color(0xFF00675D),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFCCFBF1),
    onPrimaryContainer = Color(0xFF003B33),
    background = Color(0xFFF4F7FA),
    onBackground = NextHireNavy,
    surface = Color.White,
    onSurface = NextHireNavy,
    onSurfaceVariant = Color(0xFF526579),
    outlineVariant = Color(0xFFDDE5ED)
)

private val DarkColors = darkColorScheme(
    primary = NextHireMint,
    onPrimary = Color(0xFF003B33),
    primaryContainer = Color(0xFF005047),
    onPrimaryContainer = Color(0xFFCCFBF1),
    background = Color(0xFF0D1826),
    onBackground = Color(0xFFE8F0F7),
    surface = Color(0xFF172638),
    onSurface = Color(0xFFE8F0F7),
    onSurfaceVariant = Color(0xFFB1C2D4),
    outlineVariant = Color(0xFF34485D)
)

@Composable
fun NextHireTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}