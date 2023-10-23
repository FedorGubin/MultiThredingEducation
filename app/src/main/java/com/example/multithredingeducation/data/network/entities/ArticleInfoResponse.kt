package com.example.multithredingeducation.data.network.entities

import com.example.multithredingeducation.domain.entities.Article
import com.example.multithredingeducation.domain.entities.ArticleInfo
import com.google.gson.annotations.SerializedName

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

val toDomainModel: ArticleInfoResponse.() -> ArticleInfo = {
    ArticleInfo(
        copyright = copyright,
        articles = response.docs.map { responseArticle ->
            Article(
                webLink = responseArticle.webUrl,
                snippet = responseArticle.snippet
            )
        }
    )
}