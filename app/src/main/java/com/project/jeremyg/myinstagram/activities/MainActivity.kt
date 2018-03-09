package com.project.jeremyg.myinstagram.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.project.jeremyg.myinstagram.R
import com.project.jeremyg.myinstagram.fragments.GridFragment



class MainActivity : AppCompatActivity() {

    var currentPosition: Int = 0
    private val KEY_CURRENT_POSITION = "com.project.jeremyg.myinstagram.key.currentPosition"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION, 0)
            // Return here to prevent adding additional GridFragments when changing orientation
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
}
