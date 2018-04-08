package com.project.jeremyg.myinstagram.di.modules

import android.content.Context
import com.project.jeremyg.myinstagram.utils.IOHelpers
import dagger.Module
import dagger.Provides


@Module
class IOModule () {

    @Provides
    fun provideIOHelpers(context: Context) : IOHelpers {
        return IOHelpers(context)
    }
}