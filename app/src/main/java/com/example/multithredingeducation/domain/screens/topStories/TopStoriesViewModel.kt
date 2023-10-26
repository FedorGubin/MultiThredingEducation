package com.example.multithredingeducation.domain.screens.topStories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.multithredingeducation.data.network.NYTimesNetworkImpl
import com.example.multithredingeducation.domain.dataInterfaces.BaseResponse
import com.example.multithredingeducation.domain.dataInterfaces.NYTimesNetwork
import com.example.multithredingeducation.domain.entities.TopStoriesInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TopStoriesViewModel(
    private val dataSource: NYTimesNetwork = NYTimesNetworkImpl()
) : ViewModel() {
    private val data: MutableLiveData<TopStoriesInfo> = MutableLiveData()
    val copyrightText: LiveData<String> = data.map {
        it.copyright
    }.distinctUntilChanged()

    // todo добавить liveData по аналогии с copyrightText в которой будет список статей

    init {
        requestTopStories()
    }

    private fun requestTopStories() {
        viewModelScope.launch(
            context = SupervisorJob() + Dispatchers.IO
        ) {
            val response = dataSource.getTopStories()
            if (response is BaseResponse.Response) {
                val newData = response.data
                data.postValue(newData)
            }
        }
    }
}