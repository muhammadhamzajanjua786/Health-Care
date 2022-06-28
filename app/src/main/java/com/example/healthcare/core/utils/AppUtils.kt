package com.example.healthcare.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.Toast
import com.example.healthcare.databinding.FragmentDashboardBinding
import java.util.*

object AppUtils {

    const val SUCCESS = "Success"
    const val LOGOUT = "Logout."
    const val ERROR_MESSAGE = "Couldn't reach server, check your internet connection and try again!"

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun enableShimmer(isShown: Boolean, binding: FragmentDashboardBinding) {
        if (isShown) {
            binding.shimmer.visibility = View.VISIBLE
        } else {
            binding.shimmer.visibility = View.GONE
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                return this.getNetworkCapabilities(this.activeNetwork)?.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_INTERNET
                ) ?: false
            } else {
                @Suppress("DEPRECATION")
                return this.activeNetworkInfo?.isConnected ?: false
            }
        }
    }

    fun greeting(): String {
        val c = Calendar.getInstance()
        val timeOfDay = c[Calendar.HOUR_OF_DAY]
        var greet = ""
        when (timeOfDay) {
            in 0..11 -> {
                greet = "Good Morning"
            }
            in 12..15 -> {
                greet = "Good Afternoon"
            }
            in 16..20 -> {
                greet = "Good Evening"
            }
            in 21..23 -> {
                greet = "Good Night"
            }
        }
        return greet
    }
}