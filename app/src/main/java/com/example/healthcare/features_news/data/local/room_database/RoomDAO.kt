package com.example.healthcare.features_news.data.local.room_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthcare.features_news.data.local.room_database.entities.HealthCareEntity

@Dao
interface RoomDAO {

    /***********************************************/
    /******************HEALTH_CARE******************/
    /***********************************************/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<HealthCareEntity>)
    @Query("SELECT * FROM tableHealthCare")
    fun get(): List<HealthCareEntity>

    @Query("SELECT COUNT(id) FROM tableHealthCare")
    fun count():Int

    @Query("DELETE FROM tableHealthCare")
    suspend fun delete()
}