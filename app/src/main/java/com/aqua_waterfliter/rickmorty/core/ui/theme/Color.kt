package com.aqua_waterfliter.rickmorty.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Define colors
val NeonGreen = Color(0xFF66FF66)
val NeonPurple = Color(0xFF8B00FF)
val BrightYellow = Color(0xFFFFD700)
val NeonBlue = Color(0xFF00BFFF)
val Coral = Color(0xFFFF6F61)
val LightBackground = Color(0xFFFFFFFF)
val DarkBackground = Color(0xFF121212)
val LightSurface = Color(0xFFF0F0F0)
val DarkSurface = Color(0xFF1E1E1E)

val LightColorScheme = lightColorScheme(
    primary = NeonBlue,
    onPrimary = Color.Black,
    secondary = NeonGreen,
    onSecondary = Color.White,
    background = LightBackground,
    surface = LightSurface,
    onBackground = Color.Black,
    onSurface = Color.Black,
    primaryContainer = NeonGreen.copy(alpha = 0.1f)
)

val DarkColorScheme = darkColorScheme(
    primary = NeonBlue,
    onPrimary = Color.Black,
    secondary = NeonGreen,
    onSecondary = Color.White,
    background = DarkBackground,
    surface = DarkSurface,
    onBackground = Color.White,
    onSurface = Color.White,
    primaryContainer = NeonGreen.copy(alpha = 0.2f)
)
