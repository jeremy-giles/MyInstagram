package com.project.jeremyg.myinstagram.repositories

import android.arch.lifecycle.LiveData
import android.util.Log
import javax.inject.Singleton
import com.project.jeremyg.myinstagram.api.UserWebservice
import com.project.jeremyg.myinstagram.instagram.InstagramData
import com.project.jeremyg.myinstagram.models.AccessToken
import java.util.concurrent.Executor
import javax.inject.Inject
import android.widget.Toast
import com.project.jeremyg.myinstagram.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Singleton
class UserRepository @Inject constructor(userWebservice: UserWebservice) {

    private val TAG = UserRepository::class.java.canonicalName

    private var userWebservice: UserWebservice? = userWebservice

    fun getAccessToken(requestCode: String, successHandler: (AccessToken) -> Unit, failureHandler: (Throwable?) -> Unit) {
        userWebservice?.getAccessToken(InstagramData.CLIENT_ID, InstagramData.CLIENT_SECRET_ID,
                "authorization_code", InstagramData.CALLBACK_URL, requestCode)?.enqueue(
        object : Callback<AccessToken> {
            override fun onResponse(call: Call<AccessToken>?, response: Response<AccessToken>?) {
                Log.e(TAG, "onResponse")
                response?.body()?.let {
                    successHandler(it)
                }
            }

            override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                Log.e(TAG, "onFailure")
                failureHandler(t)
            }
        })
    }
}