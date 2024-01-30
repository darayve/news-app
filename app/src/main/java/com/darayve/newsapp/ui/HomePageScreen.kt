package com.darayve.newsapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.darayve.newsapp.data.model.Article
import com.darayve.newsapp.data.network.Result
import com.darayve.newsapp.ui.theme.Scarlet
import com.darayve.newsapp.ui.theme.SilverMist
import com.darayve.newsapp.ui.theme.Typography
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomePageScreen(
    innerPadding: PaddingValues,
    newsViewModel: NewsViewModel,
    navController: NavController,
) {
    val articlesState by newsViewModel.homePageArticlesState.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }

    DisposableEffect(newsViewModel) {
        newsViewModel.getTopHeadlineArticles()
        onDispose { }
    }

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
            text = "Nenhuma notícia encontrada.",
            modifier = modifier.padding(innerPadding),
            textAlign = TextAlign.Center,
            style = Typography.bodyLarge,
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

@Composable
fun NewsListItem(modifier: Modifier = Modifier, article: Article) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = article.title!!, style = Typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Fonte: ${article.author ?: "None"}", style = Typography.bodyLarge)
        Spacer(modifier = Modifier.height(24.dp))
        Divider(color = SilverMist)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = modifier)
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, error: Throwable) {
    Column(modifier = modifier) {
        Text(
            text = error.message ?: "AN UNKNOWN ERROR OCCURRED.",
            style = Typography.bodyLarge.copy(color = Scarlet)
        )
    }
}
