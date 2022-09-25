package com.example.healthcare.features_news.data.local

import com.example.healthcare.features_news.data.local.room_database.RoomDAO
import com.example.healthcare.features_news.data.local.room_database.entities.HealthCareEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: RoomDAO) {
    suspend fun insertRecords(data: List<HealthCareEntity>) = dao.insertRecords(data)
    suspend fun getRecords(): List<HealthCareEntity> = dao.getRecords()
    suspend fun deleteRecords() = dao.deleteRecords()
}