package com.project.jeremyg.myinstagram.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Post(
        @SerializedName("id")
        @Expose
        var id: String = "",

        @SerializedName("type")
        @Expose
        var type: Type = Type.IMAGE,

        @SerializedName("comments")
        @Expose
        var comments: Int = 0,

        @SerializedName("likes")
        @Expose
        var likes: Int = 0,

        @SerializedName("images")
        @Expose
        var images: MutableList<Media> = mutableListOf(),

        @SerializedName("videos")
        @Expose
        var videos: List<Media> = emptyList()
) : Serializable {
        enum class Type {
                IMAGE, VIDEO
        }
}