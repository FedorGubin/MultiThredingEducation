package com.example.multithredingeducation.data.network.apis

import com.example.multithredingeducation.data.network.entities.ArticleInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTimesApi {

    @GET("/svc/search/v2/articlesearch.json")
    suspend fun getArticles(
        @Query("api-key") apiKey: String = "CaJoFMx4ShyyG4w18vjmLA00LcvPqlGc",
        @Query("sort") sort: String
    ): Response<ArticleInfoResponse>
}