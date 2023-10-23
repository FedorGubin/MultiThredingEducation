package com.example.multithredingeducation.domain.dataInterfaces

// тут не запаривайся. Тут главное, что это sealed interface и он
// нужен для оборачвания ответа по нашему запросу в сеть
sealed interface BaseResponse<O> {
    data class Response<O>(val data: O) : BaseResponse<O>
    class EmptyBody<O> : BaseResponse<O>
    sealed interface Error<O> : BaseResponse<O> {

        val text: String

        data class Unauthorized<O>(override val text: String) : Error<O>
        data class ManyRequest<O>(override val text: String) : Error<O>
        data class OtherError<O>(override val text: String) : Error<O>
    }
}