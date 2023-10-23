package com.example.multithredingeducation.domain.screens.articleSearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.multithredingeducation.data.articleSearch.NYTimesNetworkImpl
import com.example.multithredingeducation.domain.dataInterfaces.NYTimesNetwork
import com.example.multithredingeducation.domain.entities.ArticleInfo
import com.example.multithredingeducation.domain.entities.ArticleSort
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ArticleSearchViewModel(
    private val dataSource: NYTimesNetwork = NYTimesNetworkImpl()
) : ViewModel() {
    private val _currentSort: MutableLiveData<ArticleSort> = MediatorLiveData(ArticleSort.NEWEST).apply {
        addSource(this) { newSort ->
            requestArticles(newSort)
        }
    }
    val currentSort: LiveData<ArticleSort> = _currentSort.map { it }
    private val _articlesInfo: MutableLiveData<ArticleInfo> = MutableLiveData()
    val articlesInfo: LiveData<ArticleInfo> = _articlesInfo.map { it }

    fun setNewSort(newSort: ArticleSort) {
        if (newSort != _currentSort.value) {
            _currentSort.value = newSort
        }
    }

    private fun requestArticles(sort: ArticleSort) {
        viewModelScope.launch(
            context = SupervisorJob() + Dispatchers.IO + CoroutineName(name = "get Article Coroutine")
        ) {
            _articlesInfo.postValue(dataSource.getArticleSearch(sort))
        }
    }
}