package com.example.multithredingeducation

import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyApi {

    @GET("api")
    suspend fun getApi(): Response<Unit>

}