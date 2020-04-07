package com.michalkarmelita.buffsdk.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

sealed class RemoteData<out T> {
    data class Success<out T>(val value: T) : RemoteData<T>()
    data class GenericError(val code: Int? = null, val error: String? = null) :
        RemoteData<Nothing>()

    object Timeout : RemoteData<Nothing>()
    object IOError : RemoteData<Nothing>()
    object Unknown : RemoteData<Nothing>()
}

suspend inline fun <T> safeApiCall(crossinline responseFunction: suspend () -> T): RemoteData<out T> {
    return try {
        val response = withContext(Dispatchers.IO) { responseFunction.invoke() }
        RemoteData.Success(response)
    } catch (e: Exception) {
        return withContext(Dispatchers.Main) {
            e.printStackTrace()
            when (e) {
                is HttpException -> {
                    RemoteData.GenericError(e.code(), getErrorMessage(e.response()?.errorBody()))
                }
                is SocketTimeoutException -> RemoteData.Timeout
                is IOException -> RemoteData.IOError
                else -> RemoteData.Unknown
            }
        }
    }
}

private const val MESSAGE_KEY = "message"
private const val ERROR_KEY = "error"

fun getErrorMessage(responseBody: ResponseBody?): String {
    return try {
        val jsonObject = JSONObject(responseBody!!.string())
        when {
            jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
            jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
            else -> "Something wrong happened"
        }
    } catch (e: Exception) {
        "Something wrong happened"
    }
}