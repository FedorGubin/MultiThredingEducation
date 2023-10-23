package com.example.multithredingeducation.data.network.apis

import com.example.multithredingeducation.data.entities.ArticleInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// нужно вспомнить что такое ретрофит. Такой файлик нам нужен для отправки запросов в сеть
interface NYTimesApi {

    // обрати внимаение на suspend и что мы сразу указываем наш класс, для ответа.
    // т.е. функция не только сходит в сеть, то и распарсит наш ответ
    @GET("/svc/search/v2/articlesearch.json")
    suspend fun getArticles(
        @Query("api-key") apiKey: String = "CaJoFMx4ShyyG4w18vjmLA00LcvPqlGc",
        @Query("sort") sort: String
    ): Response<ArticleInfoResponse>
}