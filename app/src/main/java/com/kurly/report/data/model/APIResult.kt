package com.kurly.report.data.model

import retrofit2.Response

/**
 *
 * Create : Kwon IkYoung
 * Date : 2022/12/14
 */
sealed class APIResult<out T> {
    data class Success<out T>(val data: T) : APIResult<T>()
    data class Failure(val code: Int, val message: String, val raw: okhttp3.Response) : APIResult<Nothing>()
    data class NetworkError(val error: Throwable) : APIResult<Nothing>()
}

suspend inline fun <T> safeApiCall(crossinline apiBlock: suspend () -> Response<KurlyResponse<T>>): APIResult<KurlyResponse<T>> =
    try {
        val response = apiBlock()
        if (response.isSuccessful) {
            response.body()?.let {
                APIResult.Success(it)
            } ?: APIResult.Failure(response.code(), "Empty Body", response.raw())
        } else {
            APIResult.Failure(
                response.code(),
                response.message(),
                response.raw()
            )
        }
    } catch (e: Exception) {
        APIResult.NetworkError(e)
    }
