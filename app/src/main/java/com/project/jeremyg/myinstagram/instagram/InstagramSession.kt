package com.project.jeremyg.myinstagram.instagram

import android.content.Context
import android.content.SharedPreferences
import android.util.Log


class InstagramSession {

    companion object {
        private val first_time_variable = "true"
        private val first_time_variable1 = "true"
        private val SHARED = "Instagram_Preferences"
        private val API_USERNAME = "username"
        private val API_ID = "id"
        private val API_NAME = "name"
        private val API_ACCESS_TOKEN = "access_token"
    }

    private var mSharedPreferences: SharedPreferences? = null
    private var mEditor: SharedPreferences.Editor? = null
    private var mContext: Context? = null

    val username: String?
        get() = mSharedPreferences!!.getString(API_USERNAME, null)

    val id: String?
        get() = mSharedPreferences!!.getString(API_ID, null)

    val name: String?
        get() = mSharedPreferences!!.getString(API_NAME, null)

    val accessToken: String?
        get() = mSharedPreferences!!.getString(API_ACCESS_TOKEN, null)

    constructor(context: Context) {
        this.mContext = context
        mSharedPreferences = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE)
        mEditor = mSharedPreferences!!.edit()
    }

    constructor() {
        mSharedPreferences = mContext?.getSharedPreferences(SHARED, Context.MODE_PRIVATE)
        mEditor = mSharedPreferences!!.edit()
    }

    fun storeAccessToken(accessToken: String, id: String, username: String, name: String) {
        Log.i("Session", "setting first time variable to true")
        mEditor!!.putString(first_time_variable, "true")
        mEditor!!.putString(first_time_variable1, "true")
        mEditor!!.putString(API_ID, id)
        mEditor!!.putString(API_NAME, name)
        mEditor!!.putString(API_ACCESS_TOKEN, accessToken)
        mEditor!!.putString(API_USERNAME, username)
        mEditor!!.commit()
    }

    fun setFirst_time_variable() {
        Log.i("Session", "setting first time variable to false")
        mEditor!!.putString(first_time_variable, "false")
        mEditor!!.commit()
    }

    fun setFirst_time_variable1() {
        Log.i("Session", "setting first time variable to false")
        mEditor!!.putString(first_time_variable, "false")
        mEditor!!.commit()
    }

    fun resetAccessToken() {

        mEditor!!.putString(API_ID, null)
        mEditor!!.putString(API_NAME, null)
        mEditor!!.putString(API_ACCESS_TOKEN, null)
        mEditor!!.putString(API_USERNAME, null)
        Log.i("Session", "Reset setting first time variable to true")
        mEditor!!.putString(first_time_variable, "true")
        mEditor!!.putString(first_time_variable1, "true")
        mEditor!!.commit()
    }

    fun getFirst_time_variable(): String? {
        Log.i("Session", first_time_variable + " getting first time variable")
        return mSharedPreferences!!.getString(first_time_variable, null)
    }

    fun getFirst_time_variable1(): String? {
        Log.i("Session", first_time_variable + " getting first time variable1")
        return mSharedPreferences!!.getString(first_time_variable1, null)
    }
}
