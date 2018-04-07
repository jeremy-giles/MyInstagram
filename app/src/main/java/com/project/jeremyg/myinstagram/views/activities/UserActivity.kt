package com.project.jeremyg.myinstagram.views.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.project.jeremyg.myinstagram.R
import com.project.jeremyg.myinstagram.views.fragments.GridFragment
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class UserActivity : AppCompatActivity(), HasSupportFragmentInjector {

    private val KEY_CURRENT_POSITION = "com.project.jeremyg.myinstagram.key.currentPosition"

    companion object {
        internal var currentPosition: Int = 0
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        configureDagger()

        if(savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION, 0)
            return
        }

        val fragmentManager = supportFragmentManager
        fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, GridFragment(), GridFragment::class.java.simpleName)
                .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CURRENT_POSITION, currentPosition)
    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    private fun configureDagger() {
        AndroidInjection.inject(this)
    }
}
