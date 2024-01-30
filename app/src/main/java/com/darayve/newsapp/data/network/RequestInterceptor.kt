package com.darayve.newsapp.data.network

import com.darayve.newsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

object RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithApiKey =
            chain.request()
                .newBuilder()
                .header("X-Api-Key", BuildConfig.NEWS_API_KEY_DEV)
                .build()

        println("Making the request with the header: ${requestWithApiKey.headers()}")
        return chain.proceed(requestWithApiKey)
    }
}