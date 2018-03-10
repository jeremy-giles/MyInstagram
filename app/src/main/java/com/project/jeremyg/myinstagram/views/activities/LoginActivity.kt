package com.project.jeremyg.myinstagram.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.project.jeremyg.myinstagram.R
import com.project.jeremyg.myinstagram.instagram.InstagramData.AUTHORIZATION_URL

import com.project.jeremyg.myinstagram.views.dialogs.InstagramDialog

class LoginActivity : AppCompatActivity() {

    private val TAG = LoginActivity::class.simpleName

    private var mDialog: InstagramDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mDialog = InstagramDialog(this, AUTHORIZATION_URL)
        mDialog?.show()
    }

}
