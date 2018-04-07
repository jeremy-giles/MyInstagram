package com.project.jeremyg.myinstagram.di.modules

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import android.arch.lifecycle.ViewModel
import com.project.jeremyg.myinstagram.di.keys.ViewModelKey
import com.project.jeremyg.myinstagram.view_models.FactoryViewModel
import com.project.jeremyg.myinstagram.view_models.InstagramAuthViewModel
import com.project.jeremyg.myinstagram.view_models.PostsListViewModel
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(InstagramAuthViewModel::class)
    abstract fun bindInstagramAuthViewModel(instagramAuthViewModel: InstagramAuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostsListViewModel::class)
    abstract fun bindPostsListViewModel(postsListViewModel: PostsListViewModel): ViewModel

    @Binds
    abstract fun bindFactoryViewModel(factory: FactoryViewModel): ViewModelProvider.Factory
}