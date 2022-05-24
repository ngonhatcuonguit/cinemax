package com.cuongngo.cinemax.services.network

import com.cuongngo.cinemax.response.ErrorResponse
import com.cuongngo.cinemax.services.network.mapper.IMapper

/**
 * A generic class that holds a value with its loading status.
 *
 * Result is usually created by the Repository classes where they return
 * `LiveData<Result<T>>` to pass back the latest data to the UI with its fetch status.
 */

data class BaseResult<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val error: ErrorResponse? = null,
    val errorCode: Int? = null
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): BaseResult<T> {
            return BaseResult(Status.SUCCESS, data, null)
        }

        fun <T> error(
            message: String,
            data: T? = null,
            errorCode: Int? = null,
            error: ErrorResponse? = null
        ): BaseResult<T> {
            return BaseResult(Status.ERROR, data, message, error = error, errorCode = errorCode)
        }

        fun <T> loading(data: T? = null): BaseResult<T> {
            return BaseResult(Status.LOADING, data, null)
        }

    }

}

fun <T : Any?, O> BaseResult<T>.mapTo(mapper: IMapper<T, O>): BaseResult<O> {
    return if (this.status == BaseResult.Status.LOADING) BaseResult.loading(null)
    else if (this.status == BaseResult.Status.ERROR || this.data == null) {
        BaseResult.error(
            this.message ?: "",
            errorCode = this.errorCode,
            error = null
        )
    } else {
        BaseResult.success(mapper.map(this.data))
    }
}

inline fun <reified T, R : BaseResult<T>> R.onResultReceived(
    onLoading: () -> Unit,
    onSuccess: (result: R) -> Unit,
    onError: (result: R) -> Unit
) {
    when (status) {
        BaseResult.Status.LOADING -> onLoading.invoke()
        BaseResult.Status.SUCCESS -> onSuccess.invoke(this)
        BaseResult.Status.ERROR -> onError.invoke(this)
    }
}