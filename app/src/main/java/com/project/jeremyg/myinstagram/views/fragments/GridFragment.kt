package com.project.jeremyg.myinstagram.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.project.jeremyg.myinstagram.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GridFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GridFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GridFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grid, container, false)
    }

}
