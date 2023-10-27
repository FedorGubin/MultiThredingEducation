package com.example.multithredingeducation.data.network.entities

import com.google.gson.annotations.SerializedName

data class TopStoriesInfoResponse(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("results")
    val articles: List<ArticleTopStoriesResponse>
)
data class ArticleTopStoriesResponse(
    @SerializedName("title")
    val title: String
)