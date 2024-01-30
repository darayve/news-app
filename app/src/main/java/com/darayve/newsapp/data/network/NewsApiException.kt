package com.darayve.newsapp.data.network

class NewsApiException(
    val status: String?,
    val code: String?,
    message: String?
) : Throwable(message ?: "UNKNOWN ERROR")