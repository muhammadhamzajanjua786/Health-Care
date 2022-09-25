package com.example.healthcare.common

sealed class Resource<out T> {
    object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T? = null, val status: String? = null): Resource<T>()
    data class Failure(val status: String? = null, val message: String? = null): Resource<Nothing>()
}