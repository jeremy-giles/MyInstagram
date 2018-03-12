package com.project.jeremyg.myinstagram.di.modules

import com.project.jeremyg.myinstagram.views.activities.LoginActivity
import com.project.jeremyg.myinstagram.views.activities.UserActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    internal abstract fun contributeUserActivity(): UserActivity
}