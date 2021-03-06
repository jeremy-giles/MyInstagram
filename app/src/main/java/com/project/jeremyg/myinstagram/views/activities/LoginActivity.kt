package com.project.jeremyg.myinstagram.views.activities

import android.animation.Animator
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
import android.support.v4.content.ContextCompat
import android.graphics.drawable.GradientDrawable
import android.transition.Fade
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.project.jeremyg.myinstagram.managers.AppSharedPreferences


class LoginActivity : AppCompatActivity() {

    private val TAG = LoginActivity::class.java.canonicalName

    @Inject
    lateinit var appSharedPreferences: AppSharedPreferences

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

        cb_stay_connecting.isChecked =
                appSharedPreferences.getData(getString(R.string.sp_stay_connecting))

        animate_container.animate()
                .setDuration(1000L)
                .alpha(1F)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) {

                    }

                    override fun onAnimationCancel(p0: Animator?) {

                    }

                    override fun onAnimationStart(p0: Animator?) {
                        animate_container.alpha = 0F
                        animate_container.visibility = View.VISIBLE
                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        animate_container.visibility = View.VISIBLE
                    }

                }).start()

    }

    private fun configureViewModel() {
        authViewModel = ViewModelProviders.of(this, viewModelFactory).get(InstagramAuthViewModel::class.java)
        authViewModel!!.accessToken.observe(this, Observer<AccessToken> { accessToken ->
            if (accessToken != null) {
                appSharedPreferences.putData(getString(R.string.sp_stay_connecting),
                        cb_stay_connecting.isChecked)
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
