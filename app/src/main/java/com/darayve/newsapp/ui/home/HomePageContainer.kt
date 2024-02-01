package com.darayve.newsapp.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.darayve.newsapp.ui.components.NewsAppTopBar
import com.darayve.newsapp.ui.viewmodel.NewsViewModel
import com.darayve.newsapp.util.PermissionsHandler
import com.darayve.newsapp.util.SpeechToTextParser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageContainer(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel,
    speechToTextParser: SpeechToTextParser,
    permissionsHandler: PermissionsHandler
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                NewsAppTopBar(viewModel, scrollBehavior, speechToTextParser, permissionsHandler)
            },
        ) { innerPadding ->
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "news_list_screen"
            ) {
                composable("news_list_screen") {
                    HomePageScreen(
                        innerPadding = innerPadding,
                        newsViewModel = viewModel,
                        navController = navController
                    )
                }
                composable("favorite_news_screen") {
                    // FavoriteNewsScreen()
                }
            }
        }
    }
}
