package com.darayve.newsapp.data.network

import com.darayve.newsapp.data.model.NewsApiError
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

@Suppress("UNCHECKED_CAST")
suspend fun <T> makeCall(apiCall: suspend () -> Response<T>): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            apiCall().let { response ->
                when {
                    response.code() == HttpURLConnection.HTTP_NO_CONTENT -> Result.Success(
                        Unit
                    )

                    response.isSuccessful -> Result.Success(response.body())
                    else -> Result.Error(parseError(response))
                }
            }
        } catch (exception: Exception) {
            when (exception) {
                is HttpException -> Result.Error(parseError(exception.response()!!))

                else -> Result.Error(exception)
            }
        }
    } as Result<T>
}

private fun parseError(response: Response<*>): Throwable {
    return try {
        response.errorBody()?.charStream()?.use { reader ->
            Gson().fromJson(reader, NewsApiError::class.java)?.run {
                NewsApiException(status, code, message)
            } ?: Throwable("Error parsing response body")
        } ?: Throwable("Error body is null")
    } catch (exception: Exception) {
        HttpException(response)
    }
}
