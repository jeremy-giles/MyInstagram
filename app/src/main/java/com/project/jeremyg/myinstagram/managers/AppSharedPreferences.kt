package com.project.jeremyg.myinstagram.managers

import android.content.SharedPreferences

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSharedPreferences @Inject constructor(var mSharedPreferences: SharedPreferences) {

    fun getData(key: String): Boolean {
        return mSharedPreferences.getBoolean(key, false)
    }

    fun putData(key: String, data: Boolean) {
        mSharedPreferences.edit().putBoolean(key, data).apply()
    }
}
