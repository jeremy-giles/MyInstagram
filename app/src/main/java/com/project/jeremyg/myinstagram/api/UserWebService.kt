package com.project.jeremyg.myinstagram.api

import com.project.jeremyg.myinstagram.models.AccessToken
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST



interface UserWebservice {

    @FormUrlEncoded
    @POST("oauth/access_token")
    fun getAccessToken(
            @Field("client_id") client_id: String,
            @Field("client_secret") client_secret: String,
            @Field("grant_type") grant_type: String,
            @Field("redirect_uri") redirect_uri: String,
            @Field("code") code: String): Call<AccessToken>
}
