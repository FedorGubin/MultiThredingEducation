package com.example.multithredingeducation.data.articleSearch

import com.example.multithredingeducation.domain.dataInterfaces.NYTimesNetwork
import com.example.multithredingeducation.domain.entities.Article
import com.example.multithredingeducation.domain.entities.ArticleInfo
import com.example.multithredingeducation.domain.entities.ArticleSort

class NYTimesNetworkImpl : NYTimesNetwork {
    override suspend fun getArticleSearch(sort: ArticleSort): ArticleInfo {
        return ArticleInfo(
            copyright = "ewjnfelkrwfn",
            articles = listOf(
                Article(
                    webLink = "erljfnk",
                    snippet = "lwkrejfn",
                    imageUrl = ""
                )
            )
        )
    }
}