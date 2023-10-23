package com.example.multithredingeducation.domain.entities

data class ArticleInfo(
    val copyright: String,
    val articles: List<Article>
)

data class Article(
    val webLink: String,
    val snippet: String,
    val imageUrl: String
)
