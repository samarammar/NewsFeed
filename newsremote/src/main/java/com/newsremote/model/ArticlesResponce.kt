package com.newsremote.model

import com.newsdomain.model.Article

data class ArticlesResponce(
        val articles: List<Article>,
        val sortBy: String,
        val source: String,
        val status: String
)