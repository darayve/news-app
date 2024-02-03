package com.darayve.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.darayve.newsapp.data.network.NewsAPI
import com.darayve.newsapp.data.network.Result
import com.darayve.newsapp.data.repository.NewsRepository
import com.darayve.newsapp.data.repository.NewsRepositoryImpl
import com.darayve.newsapp.domain.Article
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class NewsViewModel(
    private val newsRepository: NewsRepository,
) : ViewModel() {
    private val _homePageArticlesState = MutableStateFlow<Result<List<Article>>>(Result.Loading)
    val homePageArticlesState = _homePageArticlesState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isSearchModeActive = MutableStateFlow(false)
    val isSearchModeActive = _isSearchModeActive.asStateFlow()

    init {
        viewModelScope.launch {
            getTopHeadlineArticles()
            _searchQuery.debounce(320).collect { query ->
                if (query.isNotEmpty()) getArticlesByQuery()
            }
        }
    }

    fun getTopHeadlineArticles() {
        viewModelScope.launch {
            newsRepository.getTopHeadlineArticles()
                .catch { error -> _homePageArticlesState.update { Result.Error(error) } }
                .collect { result -> _homePageArticlesState.update { result } }
        }
    }

    private fun getArticlesByQuery() {
        viewModelScope.launch {
            newsRepository.getArticleByQuery(query = _searchQuery.value.trim())
                .catch { error -> _homePageArticlesState.update { Result.Error(error) } }
                .collect { result -> _homePageArticlesState.update { result } }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.update { query }
    }

    fun toggleSearchModeActivation() {
        _isSearchModeActive.update { !_isSearchModeActive.value }
        _searchQuery.update { "" }
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
