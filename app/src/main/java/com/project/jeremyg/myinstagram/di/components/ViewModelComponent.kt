package com.project.jeremyg.myinstagram.di.components

import com.project.jeremyg.myinstagram.di.modules.ViewModelModule
import com.project.jeremyg.myinstagram.view_models.InstagramAuthViewModel
import com.project.jeremyg.myinstagram.view_models.PostsListViewModel
import dagger.Component

@Component(modules = arrayOf(
        ViewModelModule::class
))
interface ViewModelComponent {
    // inject my view models
    fun inject(instagramAuthViewModel: InstagramAuthViewModel)
    fun inject(postsListViewModel: PostsListViewModel)
}