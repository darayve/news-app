package com.darayve.newsapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.darayve.newsapp.ui.viewmodel.NewsViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NewsAppTopBar(
    viewModel: NewsViewModel,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val isSearchModeActive by viewModel.isSearchModeActive.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    TopAppBar(
        title = {
            if (isSearchModeActive) {
                SearchField(
                    searchQuery = searchQuery,
                    onSearchQueryChange = { query ->
                        viewModel.setSearchQuery(query = query)
                    },
                    onMicClick = {
                        // TODO: IMPLEMENT TEXT TO SPEECH
                    })
            } else {
                Text(text = "News App")
            }
        },
        navigationIcon = {
            if (isSearchModeActive) {
                IconButton(onClick = { viewModel.toggleSearchModeActivation() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Exit search mode"
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        actions = {
            if (!isSearchModeActive) {
                IconButton(onClick = { viewModel.toggleSearchModeActivation() }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search for articles"
                    )
                }
            }
        }
    )
}
