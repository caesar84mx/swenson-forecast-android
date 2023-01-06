package com.caesar84mx.swensonforecast.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = ThemeVariant,
    primaryVariant = ThemeVariant,
    secondary = Secondary,
    background = ThemeVariant,
    surface = Surface,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = OnSurface,
)

@Composable
fun SwensonForecastTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}