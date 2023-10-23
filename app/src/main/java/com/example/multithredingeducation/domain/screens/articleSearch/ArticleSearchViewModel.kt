package com.example.multithredingeducation.domain.screens.articleSearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.multithredingeducation.data.network.NYTimesNetworkImpl
import com.example.multithredingeducation.domain.dataInterfaces.BaseResponse
import com.example.multithredingeducation.domain.dataInterfaces.NYTimesNetwork
import com.example.multithredingeducation.domain.entities.ArticleSort
import com.example.multithredingeducation.domain.screens.articleSearch.entities.ArticleSearchViewState
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ArticleSearchViewModel(
    // задаём переменной источник данных. NYTimesNetwork - это интерфейс и находится
    // в доменном слое. Диктует условия общения с ним (с доменным слоем)
    // NYTimesNetworkImpl - это уже конкретная реализация данного интерфейса. Удобно, что
    // при тестировании можем подменить на что то другое
    private val dataSource: NYTimesNetwork = NYTimesNetworkImpl()
) : ViewModel() {

    // обычная LiveData. Содержит текущюю сотрировку, выбранную юзером
    private val currentSort: MutableLiveData<ArticleSort> = MutableLiveData(ArticleSort.NEWEST)

    // уже MediatorLiveData, которая модет подписываться на другие LiveDates
    private val _state: MediatorLiveData<ArticleSearchViewState> =
        // тут используется scope функция. одна из четырёх (apply)
        MediatorLiveData(ArticleSearchViewState()).apply {
            // а это пописка на другую LiveData. Посмотри, что ожидает метод
            // addSource получить в качестве аргументов
            addSource(currentSort) { newSort ->
                value = value?.copy(currentSort = newSort)
                requestArticles(newSort)
            }
        }

    // Открытая LiveData, что бы на неё можно было подписаться извне
    val state: LiveData<ArticleSearchViewState> = _state.map { it }

    // тут надеюсь понятно))
    // ArticleSort - enum class. Знает сколько у него инстансов
    fun setNewSort(newSort: ArticleSort) {
        if (newSort != currentSort.value) {
            currentSort.value = newSort
        }
    }

    // функция запроса новых статей.
    private fun requestArticles(sort: ArticleSort) {
        // устанавливаем в состояние загрузку. К слову метод copy сам сгенерировался
        // из-за того, что мы юзаем data class
        _state.value = _state.value?.copy(isLoading = true)
        // используем один из корутин билдеров.
        // SupervisorJob - для того, что бы приложение не упало при исключении внутри корутины
        // Dispatchers.IO - фоновый пул потоков для похода в сеть
        viewModelScope.launch(
            context = SupervisorJob() + Dispatchers.IO + CoroutineName(name = "get Article Coroutine")
        ) {
            // getArticleSearch функция, которая делает запрос и возвращает ответ
            // ответ обёрнут в специальный sealed interface (знает, сколько у него наследников)
            // и мы обрабатываем все ситуации, которые могут прийти из сети
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