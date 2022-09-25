package com.example.healthcare.domain.repository

import com.example.healthcare.common.Resource
import com.example.healthcare.features_news.data.remote.response.Result
import kotlinx.coroutines.flow.Flow

interface HealthCareRepository {
    suspend fun getRecords(): Flow<Resource<List<Result>>>
}