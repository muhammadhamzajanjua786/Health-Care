package com.example.healthcare.common

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}