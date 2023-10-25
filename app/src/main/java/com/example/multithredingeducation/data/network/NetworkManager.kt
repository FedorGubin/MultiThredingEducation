package com.example.multithredingeducation.data.network

import com.example.multithredingeducation.data.network.apis.NYTimesApi
import com.example.multithredingeducation.domain.dataInterfaces.BaseResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

// в этот класс особо не вникай. Тут всякие чудеса для работы сети происходят
class NetworkManager {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val apiQueryInterceptor: Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val urlWithQuery = originalRequest.url.newBuilder()
            .addQueryParameter("api-key", "CaJoFMx4ShyyG4w18vjmLA00LcvPqlGc")
            .build()
        val request = originalRequest.newBuilder().url(urlWithQuery).build()
        chain.proceed(request)
    }

    private val nytimesRetrofitClient = Retrofit.Builder()
        .baseUrl("https://api.nytimes.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder()
            .addInterceptor(apiQueryInterceptor)
            .addInterceptor(interceptor)
            .build()
        )
        .build()

    val nytimesApi: NYTimesApi = nytimesRetrofitClient.create()

    fun <I, O>handleResponse(response: Response<I>, mapper: I.() -> O) : BaseResponse<O> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                BaseResponse.EmptyBody()
            } else {
                BaseResponse.Response(data = body.mapper())
            }
        } else {
            val errorText = response.errorBody()?.string()
            when (response.code()) {
                401 -> BaseResponse.Error.Unauthorized(text = errorText ?: "Ошибка авторизации")
                429 -> BaseResponse.Error.ManyRequest(text = errorText ?: "Превышено количество запросов")
                else -> BaseResponse.Error.OtherError(text = errorText ?: "Неизвестная ошибка")
            }
        }
    }
}