package com.project.jeremyg.myinstagram

import android.app.Activity
import android.app.Application
import android.content.Context
import com.project.jeremyg.myinstagram.di.components.DaggerAppComponent
import com.project.jeremyg.myinstagram.di.modules.AppModule
import com.project.jeremyg.myinstagram.di.modules.SharedPreferencesModule
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        this.initDagger()
        context = applicationContext
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    private fun initDagger() {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build().inject(this)
    }

}