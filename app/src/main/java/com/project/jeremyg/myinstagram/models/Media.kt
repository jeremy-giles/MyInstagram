package com.project.jeremyg.myinstagram.models

enum class Resolution {
    LOW_RESOLUTION, THUMBNAIL, STANDARD
}

data class Media(
        var resolution: Resolution,
        var url: String,
        var width: Int,
        var height: Int
)