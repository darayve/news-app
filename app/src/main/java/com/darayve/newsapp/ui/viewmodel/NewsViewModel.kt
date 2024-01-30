package com.darayve.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.darayve.newsapp.data.model.Article
import com.darayve.newsapp.data.network.NewsAPI
import com.darayve.newsapp.data.network.Result
import com.darayve.newsapp.data.repository.NewsRepository
import com.darayve.newsapp.data.repository.NewsRepositoryImpl
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    private val _homePageArticlesState = MutableStateFlow<Result<List<Article>>>(Result.Loading)
    val homePageArticlesState: StateFlow<Result<List<Article>>> = _homePageArticlesState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val _isSearchModeActive = MutableStateFlow(false)
    val isSearchModeActive: StateFlow<Boolean> get() = _isSearchModeActive

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
                .catch { error -> _homePageArticlesState.value = Result.Error(error) }
                .collect { result -> _homePageArticlesState.value = result }
        }
    }

    private fun getArticlesByQuery() {
        viewModelScope.launch {
            newsRepository.getArticleByQuery(query = _searchQuery.value)
                .catch { error -> _homePageArticlesState.value = Result.Error(error) }
                .collect { result -> _homePageArticlesState.value = result }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleSearchModeActivation() {
        _isSearchModeActive.value = !_isSearchModeActive.value
        _searchQuery.value = ""
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
