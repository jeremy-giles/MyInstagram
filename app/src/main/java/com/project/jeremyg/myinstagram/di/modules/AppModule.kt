package com.project.jeremyg.myinstagram.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.project.jeremyg.myinstagram.api.UserWebservice
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton




@Module(includes = arrayOf(ViewModelModule::class))
class AppModule {

    /** NETWORK INJECTION **/

    private val BASE_URL : String = "https://api.instagram.com/"

    var mCallbackUrl = "instagram://connect"

    @Provides
    fun provideGson() : Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build()
        return retrofit
    }

    @Provides
    @Singleton
    fun provideApiWebservice(restAdapter: Retrofit) : UserWebservice {
        return restAdapter.create(UserWebservice::class.java)
    }
}