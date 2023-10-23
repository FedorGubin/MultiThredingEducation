package com.example.multithredingeducation.domain.dataInterfaces

import com.example.multithredingeducation.domain.entities.ArticleInfo
import com.example.multithredingeducation.domain.entities.ArticleSort

interface NYTimesNetwork {
    suspend fun getArticleSearch(sort: ArticleSort): ArticleInfo
}