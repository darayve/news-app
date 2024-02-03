package com.darayve.newsapp.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import com.darayve.newsapp.data.network.Result
import com.darayve.newsapp.domain.Article
import com.darayve.newsapp.ui.ErrorScreen
import com.darayve.newsapp.ui.LoadingScreen
import com.darayve.newsapp.ui.components.NewsListItem
import com.darayve.newsapp.ui.theme.SilverMist
import com.darayve.newsapp.ui.viewmodel.NewsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomePageScreen(
    innerPadding: PaddingValues,
    newsViewModel: NewsViewModel,
    onNavigateToArticle: (String) -> Unit,
) {
    val articlesState by newsViewModel.homePageArticlesState.collectAsState()
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
                onItemListClick = onNavigateToArticle
            )
        }
    }
}

@Composable
fun NewsListSection(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    innerPadding: PaddingValues,
    onItemListClick: (String) -> Unit
) {
    if (articles.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No articles were found. Try searching again.",
                modifier = modifier
                    .padding(innerPadding),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    } else {
        LazyColumn(modifier = modifier) {
            itemsIndexed(articles) { index, item ->
                if (index == 0) {
                    NewsListItem(
                        article = item,
                        modifier = Modifier
                            .padding(top = innerPadding.calculateTopPadding())
                            .clickable { onItemListClick(item.url) }
                    )
                } else {
                    NewsListItem(
                        modifier = Modifier.clickable { onItemListClick(item.url) },
                        article = item
                    )
                }
                DashedDivider()
            }
        }
    }
}

@Composable
private fun DashedDivider() {
    val pathEffect =
        PathEffect.dashPathEffect(
            intervals = floatArrayOf(10f, 10f),
            phase = 0f
        )
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onDraw = {
            drawLine(
                strokeWidth = 3f,
                color = SilverMist,
                start = Offset.Zero,
                end = Offset(size.width, 0f),
                pathEffect = pathEffect
            )
        }
    )
}
