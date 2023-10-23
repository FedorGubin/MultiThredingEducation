package com.example.multithredingeducation.data.articleSearch

import com.example.multithredingeducation.data.network.NetworkManager
import com.example.multithredingeducation.data.network.entities.toDomainModel
import com.example.multithredingeducation.domain.dataInterfaces.BaseResponse
import com.example.multithredingeducation.domain.dataInterfaces.NYTimesNetwork
import com.example.multithredingeducation.domain.entities.ArticleInfo
import com.example.multithredingeducation.domain.entities.ArticleSort

class NYTimesNetworkImpl : NYTimesNetwork {
    override suspend fun getArticleSearch(sort: ArticleSort): BaseResponse<ArticleInfo> {
        val manager = NetworkManager()
        val response = manager.nytimesApi.getArticles(
            sort = when (sort) {
                ArticleSort.NEWEST -> "newest"
                ArticleSort.OLDEST -> "oldest"
                ArticleSort.RELEVANCE -> "relevance"
            }
        )
        return manager.handleResponse(response, toDomainModel)
    }
}