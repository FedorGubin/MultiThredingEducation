package com.example.multithredingeducation.domain.dataInterfaces

import com.example.multithredingeducation.domain.entities.ArticleInfo
import com.example.multithredingeducation.domain.entities.ArticleSort
import com.example.multithredingeducation.domain.entities.TopStoriesInfo

interface NYTimesNetwork {
    suspend fun getArticleSearch(sort: ArticleSort): BaseResponse<ArticleInfo>
    suspend fun getTopStories(): BaseResponse<TopStoriesInfo>
}