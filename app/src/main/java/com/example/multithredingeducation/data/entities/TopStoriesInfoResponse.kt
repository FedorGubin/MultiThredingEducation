package com.example.multithredingeducation.data.entities

import com.google.gson.annotations.SerializedName

data class TopStoriesInfoResponse(
    @SerializedName("copyright")
    val copyright: String
    // todo добавить список статей. описание документации найти можно тут https://developer.nytimes.com/docs/articlesearch-product/1/routes/articlesearch.json/get
)