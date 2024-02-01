package com.darayve.newsapp.util

data class SpeechToTextState(
    val spokenText: String = "",
    val isSpeaking: Boolean = false,
    val error: String? = null
)
