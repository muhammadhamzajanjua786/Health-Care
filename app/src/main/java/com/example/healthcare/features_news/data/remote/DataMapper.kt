package com.example.healthcare.features_news.data.remote

import com.example.healthcare.features_news.data.local.room_database.entities.HealthCareEntity
import com.example.healthcare.features_news.data.remote.response.Result

fun Result.mapToEntity() = HealthCareEntity(name = name, dose = dose, strength = strength)