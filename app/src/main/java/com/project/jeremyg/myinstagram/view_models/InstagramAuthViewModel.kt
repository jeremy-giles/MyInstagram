package com.project.jeremyg.myinstagram.view_models

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.project.jeremyg.myinstagram.models.AccessToken
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.project.jeremyg.myinstagram.repositories.UserRepository
import javax.inject.Inject

class InstagramAuthViewModel @Inject constructor(private var userRepo: UserRepository) : ViewModel() {

    private val TAG = InstagramAuthViewModel::class.java.canonicalName

    var isLoading = MutableLiveData<Boolean>()

    var apiError = MutableLiveData<Throwable>()

    var accessToken = MutableLiveData<AccessToken>()

    fun init(requestCode: String) {
        if(!requestCode.isNotEmpty()) {
            return
        }
        Log.e(TAG, "init()")
        getAccessToken(requestCode)
    }

    fun getAccessToken(requestCode: String) {
        isLoading.value = true
        userRepo.getAccessToken(requestCode,
                {
                    accessToken.value = it
                    isLoading.value = false
                },{
                    apiError.value = it
                    isLoading.value = false
                })
    }

}