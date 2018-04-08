package com.project.jeremyg.myinstagram.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class SharedPreferencesModule () {

    @Provides
    fun provideSharedPreferences(context: Context) : SharedPreferences {
        return context.getSharedPreferences("AppSharedPreferences", Context.MODE_PRIVATE)
    }
}
