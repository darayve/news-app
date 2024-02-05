package com.darayve.newsapp.ui.activity

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.darayve.newsapp.data.network.NewsAPI
import com.darayve.newsapp.data.network.RetrofitClient
import com.darayve.newsapp.ui.home.HomePageContainer
import com.darayve.newsapp.ui.theme.NewsAppTheme
import com.darayve.newsapp.ui.viewmodel.NewsViewModel
import com.darayve.newsapp.ui.viewmodel.NewsViewModelFactory
import com.darayve.newsapp.util.PermissionsHandler
import com.darayve.newsapp.util.SpeechToTextParser

class MainActivity : ComponentActivity() {
    private val newsViewModel by lazy {
        ViewModelProvider(
            this, NewsViewModelFactory(
                RetrofitClient.client().create(NewsAPI::class.java)
            )
        )[NewsViewModel::class.java]
    }

    private val speechToTextParser by lazy {
        SpeechToTextParser(application)
    }

    private val permissionsHandler by lazy {
        PermissionsHandler(
            requestPermission = {
                requestMicrophonePermission.launch(Manifest.permission.RECORD_AUDIO)
            },
            application = application
        )
    }

    private val requestMicrophonePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                speechToTextParser.startListening()
            }
        }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NewsAppTheme {
                HomePageContainer(
                    viewModel = newsViewModel,
                    speechToTextParser = speechToTextParser,
                    permissionsHandler = permissionsHandler
                )
            }
        }
    }
}
