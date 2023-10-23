package com.example.multithredingeducation.domain.screens.articleSearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.multithredingeducation.data.articleSearch.NYTimesNetworkImpl
import com.example.multithredingeducation.domain.dataInterfaces.BaseResponse
import com.example.multithredingeducation.domain.dataInterfaces.NYTimesNetwork
import com.example.multithredingeducation.domain.entities.ArticleSort
import com.example.multithredingeducation.domain.screens.articleSearch.entities.ArticleSearchViewState
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ArticleSearchViewModel(
    private val dataSource: NYTimesNetwork = NYTimesNetworkImpl()
) : ViewModel() {

    private val currentSort: MutableLiveData<ArticleSort> = MutableLiveData(ArticleSort.NEWEST)

    private val _state: MediatorLiveData<ArticleSearchViewState> =
        MediatorLiveData(ArticleSearchViewState()).apply {
            addSource(currentSort) { newSort ->
                value = value?.copy(currentSort = newSort)
                requestArticles(newSort)
            }
        }

    val state: LiveData<ArticleSearchViewState> = _state.map { it }

    fun setNewSort(newSort: ArticleSort) {
        if (newSort != currentSort.value) {
            currentSort.value = newSort
        }
    }

    private fun requestArticles(sort: ArticleSort) {
        _state.value = _state.value?.copy(isLoading = true)
        viewModelScope.launch(
            context = SupervisorJob() + Dispatchers.IO + CoroutineName(name = "get Article Coroutine")
        ) {
            when (val response = dataSource.getArticleSearch(sort)) {
                is BaseResponse.Response -> {
                    _state.postValue(_state.value?.copy(
                        articles = response.data.articles,
                        copyRight = response.data.copyright,
                        isLoading = false,
                        overloadingText = if (response.data.articles.isEmpty()) "Пустой список" else null
                    ))
                }
                is BaseResponse.EmptyBody -> {
                    _state.postValue(_state.value?.copy(
                        isLoading = false,
                        overloadingText = "Пустой список"
                    ))
                }
                is BaseResponse.Error -> {
                    _state.postValue(_state.value?.copy(
                        isLoading = false,
                        overloadingText = response.text.ifEmpty { "Ошибка!" }
                    ))
                }
            }
        }
    }
}