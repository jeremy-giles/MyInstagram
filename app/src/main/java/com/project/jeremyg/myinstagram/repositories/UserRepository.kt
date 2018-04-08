package com.project.jeremyg.myinstagram.repositories

import android.util.Log
import com.google.gson.Gson
import javax.inject.Singleton
import com.project.jeremyg.myinstagram.api.UserWebservice
import com.project.jeremyg.myinstagram.instagram.InstagramData
import com.project.jeremyg.myinstagram.models.AccessToken
import javax.inject.Inject
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.project.jeremyg.myinstagram.models.Media
import com.project.jeremyg.myinstagram.models.Post
import com.project.jeremyg.myinstagram.utils.IOHelpers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.FileInputStream
import com.google.gson.reflect.TypeToken




@Singleton
class UserRepository @Inject constructor(userWebservice: UserWebservice) {

    private val TAG = UserRepository::class.java.canonicalName

    private var userWebservice: UserWebservice? = userWebservice

    @Inject
    lateinit var gsonHelpers: Gson

    @Inject
    lateinit var ioHelpers: IOHelpers

    private var accessToken: String = ""

    fun getAccessToken(requestCode: String, successHandler: (AccessToken) -> Unit, failureHandler: (Throwable?) -> Unit) {
        userWebservice?.getAccessToken(InstagramData.CLIENT_ID, InstagramData.CLIENT_SECRET_ID,
                "authorization_code", InstagramData.CALLBACK_URL, requestCode)?.enqueue(
        object : Callback<AccessToken> {
            override fun onResponse(call: Call<AccessToken>?, response: Response<AccessToken>?) {
                response?.body()?.let {
                    successHandler(it)
                    accessToken = it.accessToken
                }
            }

            override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                failureHandler(t)
            }
        })
    }

    fun getPostsList(successHandler: (MutableList<Post>) -> Unit, failureHandler: (Throwable?) -> Unit) {
        if (!accessToken.isEmpty()) {
            userWebservice?.getUserPosts(accessToken)?.enqueue(
                    object : Callback<JsonObject> {
                        override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                            response?.body()?.let {
                                var postsList = mutableListOf<Post>()

                                var posts = it.getAsJsonArray("data") as JsonArray
                                for (i in 0..(posts.size() - 1)) {
                                    val jsonPost = posts[i].asJsonObject
                                    var postObject = Post()

                                    // Id
                                    postObject.id = jsonPost.get("id").asString

                                    // Type
                                    when (jsonPost.get("type").asString) {
                                        "image" -> postObject.type = Post.Type.IMAGE
                                        "video" -> postObject.type = Post.Type.VIDEO
                                    }

                                    // Image
                                    var image = jsonPost.getAsJsonObject("images").getAsJsonObject("low_resolution")
                                    var media = Media()
                                    media.url = image.get("url").asString
                                    postObject.images.add(media)

                                    // Add post to list
                                    postsList.add(postObject)
                                }
                                saveGalleryToInternalStorage(postsList)

                                successHandler(postsList.toMutableList())
                            }
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                            failureHandler(t)
                        }
                    })
        } else {
            val postsList = getGalleryFromInternalStorage()
            if(postsList.size > 0) {
                successHandler(postsList.toMutableList())
            } else {
                failureHandler(Throwable("No gallery from internal storage"))
            }
        }

    }

    fun saveGalleryToInternalStorage(gallery: List<Post>) {
        val galleryToString = gsonHelpers.toJson(gallery)
        ioHelpers.writeGalleryToInternalStorage(galleryToString)
    }

    fun getGalleryFromInternalStorage() : List<Post> {
        val galleryFile = ioHelpers.readGalleryFromInternalStorage()
        val galleryString = FileInputStream(galleryFile).bufferedReader().use { it.readText() }

        return gsonHelpers.fromJson(galleryString, object : TypeToken<List<Post>>() {}.type) as List<Post>
    }
}