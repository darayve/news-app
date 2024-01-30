package com.darayve.newsapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.darayve.newsapp.ui.theme.Scarlet

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, error: Throwable) {
    Column(modifier = modifier) {
        Text(
            text = error.message ?: "An unknown error occurred.",
            style = MaterialTheme.typography.bodyLarge.copy(color = Scarlet)
        )
    }
}
