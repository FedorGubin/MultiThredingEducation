package com.example.multithredingeducation.data.entities

import com.example.multithredingeducation.domain.entities.Article
import com.example.multithredingeducation.domain.entities.ArticleInfo
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

// эта штука называется маппер. Она один тип данных преобразует в другой.
// Обрати внимаени, что это и функция высшего порядка и extension функция одновмеренно
// в ней то мы и преобразуем наш объект из дата сущности в доменную
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