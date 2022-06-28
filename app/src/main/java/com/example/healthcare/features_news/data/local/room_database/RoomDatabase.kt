package com.example.healthcare.features_news.data.local.room_database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.healthcare.features_news.data.local.room_database.entities.HealthCareEntity

@Database(entities = [HealthCareEntity::class],version = 1)
abstract class RoomDatabase: RoomDatabase() {
    abstract fun roomDAO(): RoomDAO
}