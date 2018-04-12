package com.project.jeremyg.myinstagram.views.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.project.jeremyg.myinstagram.R
import com.project.jeremyg.myinstagram.managers.AppSharedPreferences
import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashedActivity : AppCompatActivity() {

    private val ACTIVITY_LOGIN = 1000
    private val LOGOUT = 1001

    @Inject
    lateinit var appSharedPreferences: AppSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashed)

        if(appSharedPreferences.getData(getString(R.string.sp_stay_connecting))) {
            startActivityForResult(
                    Intent(this, UserActivity::class.java), LOGOUT)
        } else {
            startActivityForResult(
                    Intent(this, LoginActivity::class.java), ACTIVITY_LOGIN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ACTIVITY_LOGIN -> finish()
            LOGOUT -> recreate()
        }
    }
}
