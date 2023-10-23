package com.example.multithredingeducation.domain.screens.articleSearch.entities

import com.example.multithredingeducation.domain.entities.Article
import com.example.multithredingeducation.domain.entities.ArticleSort

data class ArticleSearchViewState(
    val copyRight: String = "",
    val articles: List<Article> = emptyList(),
    val currentSort: ArticleSort = ArticleSort.NEWEST,
    val isLoading: Boolean = true,
    val overloadingText: String? = null
)