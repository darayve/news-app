package com.darayve.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.darayve.newsapp.data.model.Article
import com.darayve.newsapp.data.network.NewsAPI
import com.darayve.newsapp.data.network.Result
import com.darayve.newsapp.data.repository.NewsRepository
import com.darayve.newsapp.data.repository.NewsRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    private val _homePageArticlesState = MutableStateFlow<Result<List<Article>>>(Result.Loading)
    val homePageArticlesState: StateFlow<Result<List<Article>>> = _homePageArticlesState

    fun getTopHeadlineArticles() {
        viewModelScope.launch {
            newsRepository.getTopHeadlineArticles()
                .catch { error -> _homePageArticlesState.value = Result.Error(error) }
                .collect { result -> _homePageArticlesState.value = result }
        }
    }
}

class NewsViewModelFactory(private val newsAPI: NewsAPI) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(NewsRepositoryImpl(newsAPI)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

