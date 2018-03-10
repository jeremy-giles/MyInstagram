package com.project.jeremyg.myinstagram.views.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.project.jeremyg.myinstagram.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ImagePagerFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ImagePagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImagePagerFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_pager, container, false)
    }

}
