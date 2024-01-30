package com.darayve.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.darayve.newsapp.data.network.NewsAPI
import com.darayve.newsapp.data.network.RetrofitClient
import com.darayve.newsapp.ui.HomePageScreen
import com.darayve.newsapp.ui.NewsViewModel
import com.darayve.newsapp.ui.NewsViewModelFactory
import com.darayve.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newsViewModel = ViewModelProvider(
            this, NewsViewModelFactory(
                RetrofitClient.client().create(NewsAPI::class.java)
            )
        )[NewsViewModel::class.java]

        setContent {
            NewsAppTheme {
                HomePageContainer(viewModel = newsViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageContainer(modifier: Modifier = Modifier, viewModel: NewsViewModel) {
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
                TopAppBar(
                    title = { Text(text = "News App") },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Pesquisar notícia"
                            )
                        }
                    }
                )
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
