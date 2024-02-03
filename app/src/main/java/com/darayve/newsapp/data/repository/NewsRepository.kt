package com.darayve.newsapp.data.repository

import com.darayve.newsapp.data.network.Result
import com.darayve.newsapp.domain.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopHeadlineArticles(): Flow<Result<List<Article>>>
    suspend fun getArticleByQuery(query: String): Flow<Result<List<Article>>>
}