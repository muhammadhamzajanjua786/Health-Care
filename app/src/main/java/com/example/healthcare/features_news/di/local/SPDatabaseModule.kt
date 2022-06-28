package com.example.healthcare.features_news.di.local

import android.content.Context
import android.content.SharedPreferences
import com.example.healthcare.features_news.data.local.SPDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SPDatabaseModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            "HealthCareSPDatabase", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideSPDatabase(preferences: SharedPreferences) =
        SPDatabase(preferences)
}