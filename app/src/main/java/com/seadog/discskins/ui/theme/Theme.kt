package com.seadog.discskins.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Green300,
    primaryVariant = Green100,
    onPrimary = White,
    secondary = Red300,
    onSecondary = White,
    secondaryVariant = Red200,
    error = Red700,
    onError = Red50,
    background = Black,
    onBackground = White,
    surface = Black,
    onSurface = White
)

private val LightColorPalette = lightColors(
    primary = Green400,
    primaryVariant = Green200,
    onPrimary = Black,
    secondary = Red700,
    onSecondary = Black,
    secondaryVariant = Red500,
    error = Red800,
    onError = Red100,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black
)

@Composable
fun DiscSkinsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}