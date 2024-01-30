package com.darayve.newsapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.darayve.newsapp.util.TextFontProvider

private val CrimsonPro = TextStyle(
    fontFamily = TextFontProvider.crimsonProFontFamily,
    fontWeight = FontWeight.Normal,
)
private val CrimsonProMedium = CrimsonPro.copy(fontWeight = FontWeight.Bold)
private val Mulish = TextStyle(
    fontFamily = TextFontProvider.mulishFontFamily,
    fontWeight = FontWeight.Normal,
)
private val MulishMedium = Mulish.copy(fontWeight = FontWeight.Medium)

val Typography = Typography(
    bodySmall = Mulish.copy(
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    bodyMedium = Mulish.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodyLarge = Mulish.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    titleSmall = CrimsonProMedium.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    titleMedium = CrimsonProMedium.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    titleLarge = CrimsonProMedium.copy(
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    headlineSmall = CrimsonPro.copy(
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    headlineMedium = CrimsonPro.copy(
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineLarge = CrimsonPro.copy(
        fontSize = 32.sp,
        lineHeight = 40.sp
    )
)
