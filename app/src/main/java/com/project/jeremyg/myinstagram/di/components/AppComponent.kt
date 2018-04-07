package com.project.jeremyg.myinstagram.di.components

import com.project.jeremyg.myinstagram.di.modules.AppModule
import dagger.Component
import javax.inject.Singleton
import com.project.jeremyg.myinstagram.App
import com.project.jeremyg.myinstagram.di.modules.ActivityModule
import com.project.jeremyg.myinstagram.di.modules.FragmentModule

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        ActivityModule::class,
        FragmentModule::class))
interface AppComponent {
    fun inject(app: App)
}