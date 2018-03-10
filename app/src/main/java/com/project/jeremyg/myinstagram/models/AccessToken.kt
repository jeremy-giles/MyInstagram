package com.project.jeremyg.myinstagram.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AccessToken (
        @SerializedName("access_token")
        @Expose
        var accessToken: String,

        @SerializedName("user")
        @Expose
        var user: User
)