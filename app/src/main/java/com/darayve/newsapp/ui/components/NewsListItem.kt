package com.darayve.newsapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.darayve.newsapp.data.model.Article
import com.darayve.newsapp.ui.theme.SilverMist

@Composable
fun NewsListItem(modifier: Modifier = Modifier, article: Article) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = article.title!!, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        // TODO: EXTRAIR FORMATAÇÃO DOS PARAMETROS QUANDO VEM NULO OU VAZIO
        Text(text = "Author: ${article.author ?: "None"}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(24.dp))
        Divider(color = SilverMist)
    }
}