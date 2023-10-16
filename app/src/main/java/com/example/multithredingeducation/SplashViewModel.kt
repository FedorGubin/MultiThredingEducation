package com.example.multithredingeducation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {


    init {
        viewModelScope.launch {
            testCoroutine()
        }

        val job = Job()
        viewModelScope.launch(
            context = Dispatchers.IO + CoroutineName("Start get user info") + job + CoroutineExceptionHandler { context, e ->
                if (e is ArrayIndexOutOfBoundsException) {
                    println("wejfhokwjefljnokwejfow")
                }
                if (e is Exception) {
                    println("kjebfkejbfn")
                }
            }
        ) {
            launch(context = CoroutineName("Start get user info from cache"), block = {
                getNetworkInfo()
            })
            testCoroutine2()
            launch { throw Exception() }

            launch(context = CoroutineName("Start get user info from network") + Job(), block = {
                launch(context = CoroutineName("Start set user info to cache")) {
                    launch {
                        testCoroutine()
                    }
                }

                launch {
                    testCoroutine2()
                }
            })
        }
    }

    private suspend fun getNetworkInfo() {
        clearOldInfo()
        setInfoCache()
    }

    private suspend fun clearOldInfo() {

    }

    private suspend fun setInfoCache() {

    }



    private suspend fun testCoroutine() {
        delay(100)
    }

    private suspend fun testCoroutine2() {
        delay(100)
    }
}