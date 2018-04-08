package com.project.jeremyg.myinstagram.views.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.project.jeremyg.myinstagram.R
import com.project.jeremyg.myinstagram.managers.AppSharedPreferences
import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashedActivity : AppCompatActivity() {

    @Inject
    lateinit var appSharedPreferences: AppSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_instagram_background)

        if(appSharedPreferences.getData(getString(R.string.sp_stay_connecting))) {
            startActivity(Intent(this, UserActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
