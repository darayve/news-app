package com.darayve.newsapp.data.repository

import com.darayve.newsapp.data.model.Article
import com.darayve.newsapp.data.network.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopHeadlineArticles(): Flow<Result<List<Article>>>
}