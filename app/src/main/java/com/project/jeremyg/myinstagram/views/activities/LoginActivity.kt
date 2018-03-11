package com.project.jeremyg.myinstagram.views.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.project.jeremyg.myinstagram.R
import com.project.jeremyg.myinstagram.instagram.InstagramAuthListener
import com.project.jeremyg.myinstagram.instagram.InstagramData.AUTHORIZATION_URL

import com.project.jeremyg.myinstagram.views.dialogs.InstagramDialog
import android.util.Log
import com.project.jeremyg.myinstagram.models.AccessToken
import com.project.jeremyg.myinstagram.view_models.InstagramAuthViewModel
import dagger.android.AndroidInjection
import android.arch.lifecycle.ViewModelProvider
import com.project.jeremyg.myinstagram.view_models.FactoryViewModel
import javax.inject.Inject



class LoginActivity : AppCompatActivity() {

    private val TAG = LoginActivity::class.java.canonicalName

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    var authViewModel: InstagramAuthViewModel? = null

    private var mDialog: InstagramDialog? = null
    private lateinit var authListener: InstagramAuthListener

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        configureViewModel()
        configureListener()

        mDialog = InstagramDialog(this, AUTHORIZATION_URL, authListener)
        mDialog?.show()
    }

    private fun configureViewModel() {
        authViewModel = ViewModelProviders.of(this, viewModelFactory).get(InstagramAuthViewModel::class.java)
        authViewModel!!.accessToken.observe(this, Observer<AccessToken> { accessToken ->
            if (accessToken != null) {
                Log.e(TAG, "ACCESS_TOKEN: " + accessToken.accessToken)
                Log.e(TAG, "ID: " + accessToken.user.id)
                Log.e(TAG, "UserName: " + accessToken.user.userName)
            }
        })
    }

    private fun configureListener() {
        authListener = object : InstagramAuthListener {

            override fun onComplete(requestCode: String) {
                Log.d(TAG, "onSuccess: " + requestCode)
                authViewModel?.init(requestCode)
            }

            override fun onError(error: String) {
                Log.d(TAG, "onFail: " + error)
            }
        }
    }

}
