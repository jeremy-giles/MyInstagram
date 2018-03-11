package com.project.jeremyg.myinstagram.di.modules

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import com.project.jeremyg.myinstagram.view_models.UserProfileViewModel
import android.arch.lifecycle.ViewModel
import com.project.jeremyg.myinstagram.di.keys.ViewModelKey
import com.project.jeremyg.myinstagram.view_models.FactoryViewModel
import com.project.jeremyg.myinstagram.view_models.InstagramAuthViewModel
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(InstagramAuthViewModel::class)
    abstract fun bindInstagramAuthViewModel(instagramAuthViewModel: InstagramAuthViewModel): ViewModel

    @Binds
    abstract fun bindFactoryViewModel(factory: FactoryViewModel): ViewModelProvider.Factory
}