package com.darayve.newsapp.data.repository

import com.darayve.newsapp.data.model.Article
import com.darayve.newsapp.data.network.NewsAPI
import com.darayve.newsapp.data.network.Result
import com.darayve.newsapp.data.network.makeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsRepositoryImpl(private val newsAPI: NewsAPI) : NewsRepository {
    override suspend fun getTopHeadlineArticles(): Flow<Result<List<Article>>> =
        flow {
            emit(Result.Loading)
            try {
                when (val response = makeCall { newsAPI.getTopHeadlineArticles() }) {
                    is Result.Success -> emit(
                        Result.Success(
                            response.data.articles ?: emptyList<Article>()
                        )
                    )

                    is Result.Error -> emit(Result.Error(response.throwable))
                    is Result.Loading -> emit(Result.Loading)
                }
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.flowOn(Dispatchers.IO)
}
