package com.project.jeremyg.myinstagram.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class SharedPreferencesModule () {

    private lateinit var context: Context

    constructor(context: Context) : this() {
        this.context = context
    }

    @Provides
    fun provideSharedPreferences() : SharedPreferences {
        return context.getSharedPreferences("AppSharedPreferences", Context.MODE_PRIVATE)
    }
}
