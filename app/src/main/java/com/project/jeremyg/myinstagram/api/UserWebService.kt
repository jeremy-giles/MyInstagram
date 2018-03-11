package com.project.jeremyg.myinstagram.api

import com.project.jeremyg.myinstagram.models.AccessToken
import com.project.jeremyg.myinstagram.models.Post
import retrofit2.Call
import retrofit2.http.*

interface UserWebservice {

    /**
        curl -F 'client_id=CLIENT_ID' \
        -F 'client_secret=CLIENT_SECRET' \
        -F 'grant_type=authorization_code' \
        -F 'redirect_uri=AUTHORIZATION_REDIRECT_URI' \
        -F 'code=CODE' \
        https://api.instagram.com/oauth/access_token
     */
    @FormUrlEncoded
    @POST("oauth/access_token")
    fun getAccessToken(
            @Field("client_id") client_id: String,
            @Field("client_secret") client_secret: String,
            @Field("grant_type") grant_type: String,
            @Field("redirect_uri") redirect_uri: String,
            @Field("code") code: String): Call<AccessToken>

    /**
        https://api.instagram.com/v1/users/self/media/recent/?access_token=ACCESS-TOKEN
     */
    @GET("users/self/media/recent/")
    fun getUserPosts(
            @Query("access_token") accessToken: String
                    ): Call<List<Post>>
}
