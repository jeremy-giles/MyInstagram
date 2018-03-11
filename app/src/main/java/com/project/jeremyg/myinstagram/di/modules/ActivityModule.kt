package com.project.jeremyg.myinstagram.di.modules

import com.project.jeremyg.myinstagram.views.activities.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributeLoginActivity(): LoginActivity
}