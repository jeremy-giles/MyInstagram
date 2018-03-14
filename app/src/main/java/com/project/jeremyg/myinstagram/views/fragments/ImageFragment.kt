package com.project.jeremyg.myinstagram.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.project.jeremyg.myinstagram.R
import com.project.jeremyg.myinstagram.models.Post
import kotlinx.android.synthetic.main.fragment_image.*
import android.graphics.drawable.Drawable
import android.support.annotation.Nullable
import android.util.Log
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ImageFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageFragment : Fragment() {

    private val TAG = ImageFragment::class.java.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        Log.e(TAG, "onCreateView()")

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_image, container, false)

        var currentPost = arguments?.getSerializable(ARG_PARAM1) as Post
        view.findViewById<ImageView>(R.id.image).setTransitionName(currentPost.id)

        // Load the image with Glide to prevent OOM error when the image drawables are very large.
        Glide.with(this)
                .load(currentPost.images[0].url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        // The postponeEnterTransition is called on the parent ImagePagerFragment, so the
                        // startPostponedEnterTransition() should also be called on it to get the transition
                        // going in case of a failure.
                        parentFragment!!.startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: com.bumptech.glide.request.target.Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        // The postponeEnterTransition is called on the parent ImagePagerFragment, so the
                        // startPostponedEnterTransition() should also be called on it to get the transition
                        // going when the image is ready.
                        parentFragment!!.startPostponedEnterTransition()
                        return false
                    }
                })
                .into(view.findViewById(R.id.image) as ImageView)

        return view
    }

    companion object {

        private val TAG = ImageFragment::class.java.canonicalName

        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment ImageFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(post: Post?): ImageFragment {
            Log.e(TAG, "newInstance()")
            val fragment = ImageFragment()
            val args = Bundle()
            args.putSerializable(ARG_PARAM1, post)
            fragment.arguments = args
            return fragment
        }
    }
}
