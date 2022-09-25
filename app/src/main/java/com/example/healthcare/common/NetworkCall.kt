package com.example.healthcare.common

import com.example.healthcare.common.Utility.ERROR_MESSAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

fun <T> NetworkCall(call: suspend () -> Response<T>): Flow<Resource<T>> = channelFlow {
    try {
        send(Resource.Loading)
        val result = call()

        if (result.isSuccessful) {
            send(Resource.Success(data = result.body()))
        } else if (!result.isSuccessful) {
            send(Resource.Failure(message = result.message()))
        } else {
            result.errorBody()?.let { error ->
                error.close()
                send(Resource.Failure(error.toString()))
            }
        }
    } catch (e: HttpException) {
        send(Resource.Failure(message = ERROR_MESSAGE))
    } catch (e: IOException) {
        send(Resource.Failure(message = ERROR_MESSAGE))
    }
}