package com.cuongngo.cinemax.services.network

import android.util.Log
import com.cuongngo.cinemax.response.ErrorResponse
import com.cuongngo.cinemax.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.concurrent.CancellationException

/**
 * Abstract Base Data source class with error handling
 */

abstract class BaseRemoteDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): BaseResult<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null)
                    return BaseResult.success(body)
            }
            return error(
                " ${response.code()} ${response.message()}",
                response.errorBody(),
                errorCode = response.code()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return when (e) {
                is CancellationException ->
                    error(
                        e.message ?: e.toString(),
                        null,
                        errorCode = Constants.Exception.CANCELLATION_EXCEPTION
                    )
                else -> error(e.message ?: e.toString(), null, errorCode = null)
            }
        }
    }

    private fun <T> error(message: String,errorBody: ResponseBody?,errorCode:Int?): BaseResult<T> {
        Log.e("BaseRemoteDataSource","Network call has failed for a following reason: $message")
        return if(errorBody==null){
            BaseResult.error(
                "Network call has failed for a following reason: $message",
                errorCode = errorCode,
                error = null
            )
        }else {
            val gson = Gson()
            val type = object : TypeToken<ErrorResponse>() {}.type
            val errorData: ErrorResponse = gson.fromJson(errorBody.charStream(), type)
            BaseResult.error(
                "Network call has failed for a following reason: $message",
                errorCode = errorCode,
                error = errorData
            )
        }
    }
}