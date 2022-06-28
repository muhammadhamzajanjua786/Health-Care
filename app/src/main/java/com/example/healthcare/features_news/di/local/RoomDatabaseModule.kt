package com.example.healthcare.features_news.di.local

import android.content.Context
import androidx.room.Room
import com.example.healthcare.features_news.data.local.room_database.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomDatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext app: Context,
    ) = Room.databaseBuilder(
        app,
        RoomDatabase::class.java,
        "HealthCareRoomDatabase")
        .fallbackToDestructiveMigration()
        .enableMultiInstanceInvalidation()
        .build()

    @Singleton
    @Provides
    fun provideDao(database: RoomDatabase) = database.roomDAO()
}