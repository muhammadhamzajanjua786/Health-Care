package com.example.healthcare.features_news.data.remote.response

data class HealthCareResponse(
    val results: List<Result>,
    val status: Int
)