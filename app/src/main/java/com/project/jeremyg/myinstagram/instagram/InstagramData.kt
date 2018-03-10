package com.project.jeremyg.myinstagram.instagram

object InstagramData {
    val AUTH_URL = "https://api.instagram.com/oauth/authorize/"
    val TOKEN_URL = "https://api.instagram.com/oauth/access_token"
    val API_URL = "https://api.instagram.com/v1"
    var CALLBACK_URL = "https://instagram.com"

    val CLIENT_ID = "X"
    val CLIENT_SECRET_ID = "X"

    val AUTHORIZATION_URL = AUTH_URL + "?client_id=" + CLIENT_ID +
                            "&redirect_uri=" + CALLBACK_URL +
                            "&response_type=code"
}