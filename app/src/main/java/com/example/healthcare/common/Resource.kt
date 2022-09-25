package com.example.healthcare.common

sealed class Resource<out T> {
    object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T? = null, val status: String? = null): Resource<T>()
    data class Error(val exception: DataSourceException): Resource<Nothing>()
}