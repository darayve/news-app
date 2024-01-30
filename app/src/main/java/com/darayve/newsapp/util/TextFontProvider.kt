package com.darayve.newsapp.util

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.darayve.newsapp.R

object TextFontProvider {
    private val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    private val crimsonPro = GoogleFont("Crimson Pro")
    val crimsonProFontFamily = FontFamily(
        Font(
            googleFont = crimsonPro,
            fontProvider = provider
        )
    )
    private val mulish = GoogleFont("Mulish")
    val mulishFontFamily = FontFamily(
        Font(
            googleFont = mulish,
            fontProvider = provider
        )
    )

    private val CrimsonPro = TextStyle(
        fontFamily = crimsonProFontFamily,
        fontWeight = FontWeight.Normal
    )
    private val CrimsonProMedium = CrimsonPro.copy(fontWeight = FontWeight.Bold)

    private val Mulish = TextStyle(
        fontFamily = mulishFontFamily,
        fontWeight = FontWeight.Normal
    )
    private val MulishMedium = Mulish.copy(fontWeight = FontWeight.Medium)
}