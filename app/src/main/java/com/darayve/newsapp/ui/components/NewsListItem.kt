package com.darayve.newsapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.darayve.newsapp.domain.Article

@Composable
fun NewsListItem(modifier: Modifier = Modifier, article: Article) {
    Column(
        modifier = modifier
            .padding(18.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Source: ${article.source}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Author: ${article.author}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
