package com.example.healthcare.features_news.di

import android.content.Context
import com.example.healthcare.R
import com.example.healthcare.features_news.data.remote.ApiService
import com.example.healthcare.features_news.presentation.fragments.login.LoginFragment
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
object NetworkingModule {

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

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context) = context

}