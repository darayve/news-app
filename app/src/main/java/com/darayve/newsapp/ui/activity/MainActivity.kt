package com.darayve.newsapp.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.darayve.newsapp.data.network.NewsAPI
import com.darayve.newsapp.data.network.RetrofitClient
import com.darayve.newsapp.ui.home.HomePageContainer
import com.darayve.newsapp.ui.theme.NewsAppTheme
import com.darayve.newsapp.ui.viewmodel.NewsViewModel
import com.darayve.newsapp.ui.viewmodel.NewsViewModelFactory

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
