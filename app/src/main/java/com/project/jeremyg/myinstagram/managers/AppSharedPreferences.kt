package com.project.jeremyg.myinstagram.managers

import android.content.SharedPreferences

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AppSharedPreferences @Inject constructor(var mSharedPreferences: SharedPreferences) {

    fun getStringData(key: String): String {
        return mSharedPreferences.getString(key, "")
    }

    fun putData(key: String, data: String) {
        mSharedPreferences.edit().putString(key, data).apply()
    }
}
