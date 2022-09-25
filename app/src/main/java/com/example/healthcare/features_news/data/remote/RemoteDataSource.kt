package com.example.healthcare.features_news.data.remote

import com.example.healthcare.common.Resource
import com.example.healthcare.features_news.data.remote.response.HealthCareResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun getRecords(): Flow<Resource<HealthCareResponse>>
}