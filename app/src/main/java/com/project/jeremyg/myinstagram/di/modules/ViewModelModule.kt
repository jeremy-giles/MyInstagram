package com.project.jeremyg.myinstagram.di.modules

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import com.project.jeremyg.myinstagram.view_models.UserProfileViewModel
import android.arch.lifecycle.ViewModel
import com.project.jeremyg.myinstagram.di.keys.ViewModelKey
import com.project.jeremyg.myinstagram.view_models.FactoryViewModel
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    internal abstract fun bindUserProfileViewModel(repoViewModel: UserProfileViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: FactoryViewModel): ViewModelProvider.Factory
}