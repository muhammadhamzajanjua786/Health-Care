package com.example.healthcare.features_news.data.repository

import com.example.healthcare.common.Resource
import com.example.healthcare.domain.repository.HealthCareRepository
import com.example.healthcare.features_news.data.local.LocalDataSource
import com.example.healthcare.features_news.data.remote.RemoteDataSource
import com.example.healthcare.features_news.data.remote.mapToEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : HealthCareRepository {

    fun deleteRecords() = CoroutineScope(Dispatchers.IO).launch { localDataSource.deleteRecords() }

    override suspend fun getRecords() = channelFlow {
        val dbSource = localDataSource.getRecords()
        if (dbSource.isNotEmpty()) {
            channel.send(Resource.Success(data = dbSource.map { it.toResult() }))
        } else {
            remoteDataSource.getRecords().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        send(Resource.Success(data = response.data!!.results))
                        localDataSource.insertRecords(response.data.results.map { it.mapToEntity() })
                    }
                    is Resource.Failure -> send(Resource.Failure(message = response.message))
                }
            }
        }
    }
}