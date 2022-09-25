package com.example.healthcare.features_news.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SPDatabase @Inject constructor(private val preferences: SharedPreferences) {

    fun setLogin(value: Boolean) {
        preferences.edit().putBoolean("LOGIN", value).apply()
    }

    fun getLogin() = preferences.getBoolean("LOGIN", false)

    fun setEmail(value: String) {
        preferences.edit().putString("Email", value).apply()
    }

    fun getEmail() = preferences.getString("Email", "")
}