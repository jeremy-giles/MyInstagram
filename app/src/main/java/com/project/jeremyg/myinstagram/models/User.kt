package com.project.jeremyg.myinstagram.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User (
        @SerializedName("id")
        @Expose
        var id: String,

        @SerializedName("username")
        @Expose
        var userName: String,

        @SerializedName("full_name")
        @Expose
        var fullName: String
)