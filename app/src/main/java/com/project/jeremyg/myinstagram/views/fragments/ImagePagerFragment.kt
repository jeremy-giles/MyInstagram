package com.project.jeremyg.myinstagram.views.fragments

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.SharedElementCallback
import android.support.v4.view.ViewPager
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.project.jeremyg.myinstagram.R
import com.project.jeremyg.myinstagram.models.Post
import com.project.jeremyg.myinstagram.view_models.PostsListViewModel
import com.project.jeremyg.myinstagram.views.activities.UserActivity
import com.project.jeremyg.myinstagram.views.adapters.ImagePagerAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ImagePagerFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ImagePagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImagePagerFragment : Fragment() {

    lateinit var viewPager: ViewPager

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var postsListViewModel: PostsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        configureDagger()
        configureViewModel()

        viewPager = inflater.inflate(R.layout.fragment_image_pager, container, false) as ViewPager
        viewPager.adapter = ImagePagerAdapter(this, postsListViewModel)

        viewPager.setCurrentItem(UserActivity.currentPosition)
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                UserActivity.currentPosition = position
            }
        })

        prepareSharedElementTransition()

        if(savedInstanceState == null) {
            postponeEnterTransition()
        }

        return viewPager
    }

    private fun configureDagger() {
        AndroidSupportInjection.inject(this)
    }

    private fun configureViewModel() {
        postsListViewModel = ViewModelProviders.of(this.activity!!, viewModelFactory).get(PostsListViewModel::class.java)
    }

    /**
     * Prepares the shared element transition from and back to the grid fragment.
     */
    private fun prepareSharedElementTransition() {
        val transition = TransitionInflater.from(context)
                .inflateTransition(R.transition.image_shared_element_transition)
        sharedElementEnterTransition = transition

        // A similar mapping is set at the GridFragment with a setExitSharedElementCallback.
        setEnterSharedElementCallback(
                object : SharedElementCallback() {
                    override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                        // Locate the image view at the primary fragment (the ImageFragment that is currently
                        // visible). To locate the fragment, call instantiateItem with the selection position.
                        // At this stage, the method will simply return the fragment at the position and will
                        // not create a new one.
                        val currentFragment = viewPager.adapter
                                ?.instantiateItem(viewPager, UserActivity.currentPosition) as Fragment
                        val view = currentFragment.view ?: return

                        // Map the first shared element name to the child ImageView.
                        sharedElements.put(names[0], view.findViewById(R.id.image))
                    }
                })
    }
}
