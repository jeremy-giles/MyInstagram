package com.project.jeremyg.myinstagram.instagram


interface InstagramAuthListener {
        fun onComplete(accessToken: String)
        fun onError(error: String)
}
