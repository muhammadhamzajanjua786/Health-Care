package com.example.healthcare.features_news.data.local.room_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthcare.features_news.data.local.room_database.entities.HealthCareEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecords(data: List<HealthCareEntity>)
    @Query("SELECT * FROM tableHealthCare")
    suspend fun getRecords(): List<HealthCareEntity>
    @Query("DELETE FROM tableHealthCare")
    suspend fun deleteRecords()
}