package com.project.jeremyg.myinstagram.models

data class Media(
        var url: String = ""
) {
    enum class Resolution {
        LOW_RESOLUTION, THUMBNAIL, STANDARD
    }
}