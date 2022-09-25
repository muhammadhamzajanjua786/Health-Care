package com.example.healthcare.features_news.data.remote

import com.example.healthcare.common.NetworkCall
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) : RemoteDataSource {

    override suspend fun getRecords() = NetworkCall {
        apiService.getRecords()
    }
}