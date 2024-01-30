package com.darayve.newsapp.data.network

import com.darayve.newsapp.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("top-headlines?country=us")
    suspend fun getTopHeadlineArticles(): Response<NewsResponse>

    @GET("everything")
    suspend fun getArticlesByQuery(
        @Query("q") searchQuery: String,
    ): Response<NewsResponse>
}