package com.project.jeremyg.myinstagram.view_models

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.project.jeremyg.myinstagram.models.Post
import com.project.jeremyg.myinstagram.repositories.UserRepository
import javax.inject.Inject

class PostsListViewModel() : ViewModel() {

    private val TAG = PostsListViewModel::class.java.canonicalName

    lateinit var userRepo: UserRepository

    var isLoading = MutableLiveData<Boolean>()

    var apiError = MutableLiveData<Throwable>()

    var postsResponse = MutableLiveData<List<Post>>()

    @Inject
    constructor(userRepo: UserRepository) : this() {
        this.userRepo = userRepo
        init()
    }

    fun init() {
        getPostsList()
    }

    fun getPostsList() {
        isLoading.value = true
        userRepo.getPostsList(
                {
                    postsResponse.value = it
                    isLoading.value = false
                },{
                    apiError.value = it
                    isLoading.value = false
                })
    }

    /**
     * Adapter Callback
     */
    fun getPostAt(position: Int) : Post? {
        if(position < getPostsSize()) {
            return postsResponse.value?.get(position)
        } else {
            return null
        }
    }

    fun getPostsSize(): Int {
        postsResponse.value?.let {
            return it.size
        }
        return 0
    }
}
