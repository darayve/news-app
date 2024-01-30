package com.darayve.newsapp.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.darayve.newsapp.data.model.Article
import com.darayve.newsapp.data.network.Result
import com.darayve.newsapp.ui.ErrorScreen
import com.darayve.newsapp.ui.LoadingScreen
import com.darayve.newsapp.ui.components.NewsListItem
import com.darayve.newsapp.ui.viewmodel.NewsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomePageScreen(
    innerPadding: PaddingValues,
    newsViewModel: NewsViewModel,
    navController: NavController,
) {
    val articlesState by newsViewModel.homePageArticlesState.collectAsState()
    // TODO: USAR isRefreshing NO VIEW MODEL E REFATORAR
    var isRefreshing by remember { mutableStateOf(false) }

    SwipeRefresh(
        modifier = Modifier
            .fillMaxSize(),
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = {
            newsViewModel.getTopHeadlineArticles()
            isRefreshing = false
        },
    ) {
        when (articlesState) {
            is Result.Loading -> {
                LoadingScreen()
            }

            is Result.Error -> ErrorScreen(
                modifier = Modifier.padding(innerPadding),
                error = (articlesState as Result.Error).throwable
            )

            is Result.Success -> NewsListSection(
                innerPadding = innerPadding,
                modifier = Modifier,
                articles = (articlesState as Result.Success).data,
            )
        }
    }
}

@Composable
fun NewsListSection(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    innerPadding: PaddingValues
) {
    if (articles.isEmpty()) {
        Text(
            text = "No articles were found. Try searching again.",
            modifier = modifier.padding(innerPadding),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp
        )
    } else {
        LazyColumn(modifier = modifier) {
            itemsIndexed(articles) { index, item ->
                if (index == 0) {
                    NewsListItem(
                        article = item,
                        modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
                    )
                } else {
                    NewsListItem(article = item)
                }
            }
        }
    }
}
