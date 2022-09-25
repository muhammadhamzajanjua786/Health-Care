package com.example.healthcare.features_news.data.local.room_database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.healthcare.features_news.data.remote.response.Result

@Entity(tableName = "tableHealthCare")
data class HealthCareEntity(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val dose: String,
    val strength: String
) {
    fun toResult(): Result {
        return Result(
            name = name,
            dose = dose,
            strength = strength
        )
    }
}
