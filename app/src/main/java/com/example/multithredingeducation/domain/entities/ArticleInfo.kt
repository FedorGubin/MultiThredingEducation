package com.example.multithredingeducation.domain.entities

// доменная сущность. Может использоваться по всему приложению!!
data class ArticleInfo(
    val copyright: String,
    val articles: List<Article>
)

data class Article(
    val webLink: String,
    val snippet: String
)
