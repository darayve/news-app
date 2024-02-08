package com.darayve.newsapp.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.darayve.newsapp.ui.NewsScreen
import com.darayve.newsapp.ui.article.ArticleScreen
import com.darayve.newsapp.ui.components.NewsAppTopBar
import com.darayve.newsapp.ui.viewmodel.NewsViewModel
import com.darayve.newsapp.util.PermissionsHandler
import com.darayve.newsapp.util.SpeechToTextParser
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
                startDestination = NewsScreen.Homepage.name
            ) {
                composable(NewsScreen.Homepage.name) {
                    HomePageScreen(
                        innerPadding = innerPadding,
                        newsViewModel = viewModel,
                        onNavigateToArticle = { articleUrl ->
                            val encodedUrl =
                                URLEncoder.encode(articleUrl, StandardCharsets.UTF_8.toString())
                            navController.navigate("${NewsScreen.Article.name}/${encodedUrl}")
                        }
                    )
                }
                composable(
                    "${NewsScreen.Article.name}/{articleUrl}",
                    arguments = listOf(
                        navArgument("articleUrl") {
                            type = NavType.StringType
                        }
                    )
                ) { backStackEntry ->
                    val articleUrl = backStackEntry.arguments?.getString("articleUrl") ?: ""

                    ArticleScreen(
                        modifier = modifier.padding(innerPadding),
                        onNavigateUp = { navController.navigate(NewsScreen.Homepage.name) },
                        articleUrl = articleUrl
                    )
                }
            }
        }
    }
}
