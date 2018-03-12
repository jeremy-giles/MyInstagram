package com.project.jeremyg.myinstagram.instagram

object InstagramData {

    val BASE_URL : String = "https://api.instagram.com/"

    val AUTH_URL = BASE_URL + "oauth/authorize/"
    val TOKEN_URL = BASE_URL + "oauth/access_token"
    val API_URL = BASE_URL + "v1"
    var CALLBACK_URL = "https://instagram.com"

    val CLIENT_ID = "8efa70d6fd4c4798854dee3b67526e61"
    val CLIENT_SECRET_ID = "522750f8268a464f84251936db5de000"

    val AUTHORIZATION_URL = AUTH_URL + "?client_id=" + CLIENT_ID +
                            "&redirect_uri=" + CALLBACK_URL +
                            "&response_type=code"
}