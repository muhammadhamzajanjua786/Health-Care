package com.example.healthcare.common

import com.example.healthcare.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import retrofit2.Response

fun <T> NetworkCall(call: suspend () -> Response<T>): Flow<Resource<T>> = channelFlow {
    try {
        val result = call()
        if (result.isSuccessful) {
            result.body()?.let {
                send(Resource.Success(it))
            } ?: run {
                send(Resource.Error(DataSourceException.Unexpected(R.string.error_unexpected_message)))
            }
        } else {
            send(Resource.Error(DataSourceException.Server(R.string.error_server_unexpected_message)))
        }
    } catch (e: Exception) {
        send(Resource.Error(RequestErrorHandler.getRequestError(e)))
    }
}