package com.example.healthcare.features_news.data.remote

import com.example.healthcare.features_news.data.remote.response.HealthCareResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(" ")
    suspend fun getData(): Response<HealthCareResponse>
}