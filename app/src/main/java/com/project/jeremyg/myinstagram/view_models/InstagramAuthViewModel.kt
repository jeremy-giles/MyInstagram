package com.project.jeremyg.myinstagram.view_models

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.project.jeremyg.myinstagram.models.AccessToken

class InstagramAuthViewModel : ViewModel() {

    private lateinit var accessToken: LiveData<AccessToken>

    fun getUser(): LiveData<AccessToken> {
        return this.accessToken
    }
}