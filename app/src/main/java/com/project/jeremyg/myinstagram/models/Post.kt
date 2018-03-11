package com.project.jeremyg.myinstagram.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

enum class Type {
    IMAGE, VIDEO
}

data class Post(
        @SerializedName("id")
        @Expose
        var id: String,

        @SerializedName("type")
        @Expose
        var type: Type,

        @SerializedName("comments")
        @Expose
        var comments: Int,

        @SerializedName("likes")
        @Expose
        var likes: Int,

        @SerializedName("images")
        @Expose
        var media: List<Media>,

        @SerializedName("videos")
        @Expose
        var videos: List<Media>
)