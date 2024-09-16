package com.aqua_waterfliter.rickmorty.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aqua_waterfliter.rickmorty.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val PermanentMarker = FontFamily(Font(R.font.permanent_marker))
val Roboto = FontFamily(Font(R.font.roboto))
val LuckiestGuy = FontFamily(Font(R.font.luckiest_guy))

val RickAndMortyTypography = Typography(
    titleLarge = Typography().titleLarge.copy(
        fontFamily = PermanentMarker,
        fontWeight = FontWeight.Bold,
        color = BrightYellow
    ),
    bodyMedium = Typography().bodyMedium.copy(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        color = NeonBlue
    ),
    labelLarge = Typography().labelLarge.copy(
        fontFamily = LuckiestGuy,
        fontWeight = FontWeight.Normal,
        color = NeonGreen
    ),
    headlineMedium = Typography.headlineMedium.copy(
        fontFamily = PermanentMarker,
        fontWeight = FontWeight.Normal,
        color = NeonGreen
    )
)