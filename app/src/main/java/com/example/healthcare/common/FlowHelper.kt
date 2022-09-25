package com.example.healthcare.common

import com.example.healthcare.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

/** extension function for Flow Class to emit loading state before the flow starts */
fun <T> Flow<Resource<T>>.onFlowStarts() = onStart { emit(Resource.Loading) }.catch { e: Throwable ->
        e.printStackTrace()
        emit(Resource.Error(DataSourceException.Unexpected(R.string.error_client_unexpected_message)))
    }
