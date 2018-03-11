package com.project.jeremyg.myinstagram.di.components

import com.project.jeremyg.myinstagram.di.modules.AppModule
import dagger.Component
import javax.inject.Singleton
import com.project.jeremyg.myinstagram.App
import com.project.jeremyg.myinstagram.di.modules.ActivityModule

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        ActivityModule::class))
interface AppComponent {

    fun inject(app: App)
}