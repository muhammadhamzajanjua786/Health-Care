package com.example.healthcare.features_news.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.healthcare.R
import com.example.healthcare.features_news.data.local.LocalDataSource
import com.example.healthcare.features_news.data.local.SPDatabase
import com.example.healthcare.features_news.data.local.room_database.RoomDAO
import com.example.healthcare.features_news.data.local.room_database.RoomDatabase
import com.example.healthcare.features_news.data.remote.ApiService
import com.example.healthcare.features_news.data.remote.RemoteDataSourceImpl
import com.example.healthcare.features_news.data.repository.DashboardRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofitClient(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRestApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)


    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext app: Context,
    ) = Room.databaseBuilder(
        app,
        RoomDatabase::class.java,
        "HealthCareRoomDatabase"
    )
        .fallbackToDestructiveMigration()
        .enableMultiInstanceInvalidation()
        .build()

    @Singleton
    @Provides
    fun provideDao(database: RoomDatabase) = database.roomDAO()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            "HealthCareSPDatabase", Context.MODE_PRIVATE
        )

    @Singleton
    @Provides
    fun provideSPDatabase(preferences: SharedPreferences) =
        SPDatabase(preferences)

    @Singleton
    @Provides
    fun provideDashboardRepositoryImpl(
        dao: RoomDAO,
        apiService: ApiService
    ): DashboardRepositoryImpl {
        val remoteDataSource = RemoteDataSourceImpl(apiService)
        return DashboardRepositoryImpl(LocalDataSource(dao), remoteDataSource)
    }

}