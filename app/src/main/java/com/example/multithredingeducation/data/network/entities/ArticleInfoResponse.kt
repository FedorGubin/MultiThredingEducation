package com.example.multithredingeducation.data.network.entities

import com.google.gson.annotations.SerializedName


// для парсинга ответа из сети. Обрати внимание, что объекты находятся в
// дата слое и не должны попасть в доменный!! Мы их тут же должны превратить в
// в сущности доменного слоя и отдать ему в качестве ответа на запрос

data class ArticleInfoResponse(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("response")
    val response: ArticleInfoDataResponse
)

data class ArticleInfoDataResponse(
    @SerializedName("docs")
    val docs: List<ArticleResponse>
)

data class ArticleResponse(
    @SerializedName("web_url")
    val webUrl: String,
    @SerializedName("snippet")
    val snippet: String
)
