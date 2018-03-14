package com.project.jeremyg.myinstagram.di.modules

import com.project.jeremyg.myinstagram.views.fragments.GridFragment
import com.project.jeremyg.myinstagram.views.fragments.ImagePagerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributeGridFragment(): GridFragment

    @ContributesAndroidInjector
    internal abstract fun contributeImagePagerFragment(): ImagePagerFragment
}