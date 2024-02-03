package com.darayve.newsapp.domain

import com.darayve.newsapp.data.model.ArticleDTO

data class Article(
    val title: String,
    val author: String,
    val url: String,
    val source: String
)

fun ArticleDTO.toArticle(): Article =
    Article(
        title = this.title ?: "No title provided.",
        author = this.author ?: "No author provided.",
        url = this.url ?: "",
        source = this.source?.name ?: "No source provided."
    )
