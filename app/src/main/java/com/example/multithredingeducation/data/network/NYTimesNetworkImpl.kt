package com.example.multithredingeducation.data.network

import com.example.multithredingeducation.domain.dataInterfaces.BaseResponse
import com.example.multithredingeducation.domain.dataInterfaces.NYTimesNetwork
import com.example.multithredingeducation.domain.entities.Article
import com.example.multithredingeducation.domain.entities.ArticleInfo
import com.example.multithredingeducation.domain.entities.ArticleSort
import com.example.multithredingeducation.domain.entities.TopStoriesInfo

// Это мы уже в дата слое и расширили интерфейс из доменного, что бы понять,
// как нам взаимодействовать с ним
class NYTimesNetworkImpl : NYTimesNetwork {

    // запрос в сеть происходит тут
    override suspend fun getArticleSearch(sort: ArticleSort): BaseResponse<ArticleInfo> {
        val manager = NetworkManager()
        val response = manager.nytimesApi.getArticles(
            // тут нам пригождается, что ArticleSort - это enum class и у него
            // конкретное количество инстансов. И мы их тут же преобразовываем
            // в строки т.к. сейчас нужно будет это засунуть в ссылку
            sort = when (sort) {
                ArticleSort.NEWEST -> "newest"
                ArticleSort.OLDEST -> "oldest"
                ArticleSort.RELEVANCE -> "relevance"
            }
        )
        // ну и тут полученные ответ мы преобразовываем в доменную сущность
        return manager.handleResponse(response) {
            ArticleInfo(
                copyright = this.copyright,
                articles = this.response.docs.map { responseArticle ->
                    Article(
                        webLink = responseArticle.webUrl,
                        snippet = responseArticle.snippet
                    )
                }
            )
        }
    }

    override suspend fun getTopStories(): BaseResponse<TopStoriesInfo> {
        val manager = NetworkManager()
        val response = manager.nytimesApi.getTopStories()
        return manager.handleResponse(response) {
            TopStoriesInfo(
                copyright = copyright
                // todo добавить в маппер запись списка статей
            )
        }
    }
}