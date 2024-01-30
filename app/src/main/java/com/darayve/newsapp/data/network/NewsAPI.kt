package com.darayve.newsapp.data.network

import com.darayve.newsapp.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsAPI {
    @GET("top-headlines?country=br")
    suspend fun getTopHeadlineArticles(): Response<NewsResponse>

}