package com.example.fitbodinterview.utils

import android.util.Log
import com.example.fitbodinterview.BuildConfig

object Logger {

    private const val TAG = "SampleApplication"

    fun d(message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message)
        }
    }

    fun w(message: String, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, message, throwable)
        }
    }
}