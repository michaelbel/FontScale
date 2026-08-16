package org.michaelbel.fontscale

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.expressiveLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val darkColorScheme = darkColorScheme().copy(
        background = Color(0xFF000000),
        secondary = Color(0xFF0D0D0D),
        surfaceContainerHighest = Color(0xFF212121)
    )
    val expressiveLightColorScheme = expressiveLightColorScheme().copy(
        background = Color(0xFFF0F0F0),
        secondary = Color(0xFFF8F8F8),
        surfaceContainerHighest = Color(0xFFFFFFFF)
    )

    MaterialExpressiveTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme else expressiveLightColorScheme,
        content = content
    )
}
