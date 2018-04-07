package com.project.jeremyg.myinstagram.views.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.project.jeremyg.myinstagram.R
import com.project.jeremyg.myinstagram.instagram.InstagramAuthListener
import com.project.jeremyg.myinstagram.instagram.InstagramData.AUTHORIZATION_URL

import com.project.jeremyg.myinstagram.views.dialogs.InstagramDialog
import com.project.jeremyg.myinstagram.models.AccessToken
import com.project.jeremyg.myinstagram.view_models.InstagramAuthViewModel
import dagger.android.AndroidInjection
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject


class LoginActivity : AppCompatActivity() {

    private val TAG = LoginActivity::class.java.canonicalName

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    var authViewModel: InstagramAuthViewModel? = null

    @BindView(R.id.tv_information) @JvmField
    var tvInformation: TextView? = null

    private var mDialog: InstagramDialog? = null
    private lateinit var authListener: InstagramAuthListener

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        ButterKnife.bind(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        configureViewModel()
        configureListener()

        btn_login.setOnClickListener {
            launchLogin()
        }
    }

    private fun configureViewModel() {
        authViewModel = ViewModelProviders.of(this, viewModelFactory).get(InstagramAuthViewModel::class.java)
        authViewModel!!.accessToken.observe(this, Observer<AccessToken> { accessToken ->
            if (accessToken != null) {
                startActivity(Intent(this, UserActivity::class.java))
            }
        })
    }

    private fun configureListener() {
        authListener = object : InstagramAuthListener {

            override fun onComplete(requestCode: String) {
                authViewModel?.init(requestCode)
            }

            override fun onError(error: String) {
                tvInformation?.text = getString(R.string.connection_failed)
            }
        }
    }

    internal fun launchLogin() {
        mDialog = InstagramDialog(this, AUTHORIZATION_URL, authListener)
        if(!mDialog!!.isShowing) {
            mDialog?.show()
        }
    }

}
