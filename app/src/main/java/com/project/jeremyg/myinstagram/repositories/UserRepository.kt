package com.project.jeremyg.myinstagram.repositories

import android.arch.lifecycle.LiveData
import android.util.Log
import javax.inject.Singleton
import com.project.jeremyg.myinstagram.api.UserWebservice
import com.project.jeremyg.myinstagram.instagram.InstagramData
import com.project.jeremyg.myinstagram.models.AccessToken
import java.util.concurrent.Executor
import javax.inject.Inject
import android.widget.Toast
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.project.jeremyg.myinstagram.App
import com.project.jeremyg.myinstagram.models.Media
import com.project.jeremyg.myinstagram.models.Post
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Singleton
class UserRepository @Inject constructor(userWebservice: UserWebservice) {

    private val TAG = UserRepository::class.java.canonicalName

    private var userWebservice: UserWebservice? = userWebservice

    private lateinit var accessToken: String

    fun getAccessToken(requestCode: String, successHandler: (AccessToken) -> Unit, failureHandler: (Throwable?) -> Unit) {
        userWebservice?.getAccessToken(InstagramData.CLIENT_ID, InstagramData.CLIENT_SECRET_ID,
                "authorization_code", InstagramData.CALLBACK_URL, requestCode)?.enqueue(
        object : Callback<AccessToken> {
            override fun onResponse(call: Call<AccessToken>?, response: Response<AccessToken>?) {
                Log.e(TAG, "onResponse")
                response?.body()?.let {
                    successHandler(it)
                    accessToken = it.accessToken
                }
            }

            override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                Log.e(TAG, "onFailure")
                failureHandler(t)
            }
        })
    }

    fun getPostsList(successHandler: (MutableList<Post>) -> Unit, failureHandler: (Throwable?) -> Unit) {
        // TODO get access token
        Log.e(TAG, "getPostsList: " + accessToken)
        userWebservice?.getUserPosts(accessToken)?.enqueue(
            object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                    response?.body()?.let {
                        Log.e(TAG, "JSONObject: " + it.toString())
                        var postsList = mutableListOf<Post>()

                        var posts = it.getAsJsonArray("data") as JsonArray
                        for(i in 0..(posts.size() - 1)) {
                            val jsonPost = posts[i].asJsonObject
                            Log.e(TAG, "Media ID: " + jsonPost.get("id"))
                            var postObject = Post()

                            // ID
                            postObject.id = jsonPost.get("id").asString

                            // TYPE
                            when(jsonPost.get("type").asString) {
                                "image" -> postObject.type = Post.Type.IMAGE
                                "video" -> postObject.type = Post.Type.VIDEO
                            }

                            // IMAGE
                            var image = jsonPost.getAsJsonObject("images").getAsJsonObject("low_resolution")
                            var media = Media()
                            media.url = image.get("url").asString
                            postObject.images.add(media)

                            // ADD POST IN LIST
                            postsList.add(postObject)
                        }

                        successHandler(postsList.toMutableList())
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e(TAG, "onFailure - getPostsList" + t.message)
                    failureHandler(t)
                }
            })

    }
}