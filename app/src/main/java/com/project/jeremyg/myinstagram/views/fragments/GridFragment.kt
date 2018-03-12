package com.project.jeremyg.myinstagram.views.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.project.jeremyg.myinstagram.R
import android.support.v7.widget.RecyclerView
import android.view.View.OnLayoutChangeListener
import android.support.v4.app.SharedElementCallback
import android.transition.TransitionInflater
import android.util.Log
import com.project.jeremyg.myinstagram.models.AccessToken
import com.project.jeremyg.myinstagram.models.Post
import com.project.jeremyg.myinstagram.view_models.PostsListViewModel
import com.project.jeremyg.myinstagram.views.activities.LoginActivity
import com.project.jeremyg.myinstagram.views.activities.UserActivity
import com.project.jeremyg.myinstagram.views.adapters.GridAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GridFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GridFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GridFragment : Fragment() {

    private val TAG = GridFragment::class.java.canonicalName

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    var postsListViewModel: PostsListViewModel? = null

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        this.configureDagger()
        this.configureViewModel()

        // Inflate the layout for this fragment
        recyclerView = (inflater.inflate(R.layout.fragment_grid, container, false) as RecyclerView?)!!
        recyclerView.adapter = GridAdapter(this, postsListViewModel)

        prepareTransitions()
        postponeEnterTransition()

        return recyclerView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun configureDagger() {
        AndroidSupportInjection.inject(this)
    }

    private fun configureViewModel() {
        postsListViewModel = ViewModelProviders.of(this, viewModelFactory).get(PostsListViewModel::class.java)
        postsListViewModel!!.postsResponse.observe(this, Observer<List<Post>> { posts ->
            if (posts != null) {
                Log.e(TAG, "ACCESS_TOKEN: ")
                recyclerView.adapter.notifyDataSetChanged()
            }
        })
        postsListViewModel?.init()
    }

    /**
     * Scrolls the recycler view to show the last viewed item in the grid. This is important when
     * navigating back from the grid.
     */
    private fun scrollToPosition() {
        recyclerView.addOnLayoutChangeListener(object : OnLayoutChangeListener {
            override fun onLayoutChange(v: View,
                                        left: Int,
                                        top: Int,
                                        right: Int,
                                        bottom: Int,
                                        oldLeft: Int,
                                        oldTop: Int,
                                        oldRight: Int,
                                        oldBottom: Int) {
                recyclerView.removeOnLayoutChangeListener(this)
                val layoutManager = recyclerView.getLayoutManager()
                val viewAtPosition = layoutManager.findViewByPosition(UserActivity.currentPosition)
                // Scroll to position if the view for the current position is null (not currently part of
                // layout manager children), or it's not completely visible.
                if (viewAtPosition == null || layoutManager
                        .isViewPartiallyVisible(viewAtPosition, false, true)) {
                    recyclerView.post { layoutManager.scrollToPosition(UserActivity.currentPosition) }
                }
            }
        })
    }

    /**
     * Prepares the shared element transition to the pager fragment, as well as the other transitions
     * that affect the flow.
     */
    private fun prepareTransitions() {
        exitTransition = TransitionInflater.from(context)
                .inflateTransition(R.transition.grid_exit_transition)

        // A similar mapping is set at the ImagePagerFragment with a setEnterSharedElementCallback.
        setExitSharedElementCallback(
                object : SharedElementCallback() {
                    override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                        // Locate the ViewHolder for the clicked position.
                        val selectedViewHolder = recyclerView
                                .findViewHolderForAdapterPosition(UserActivity.currentPosition)
                        if (selectedViewHolder == null || selectedViewHolder.itemView == null) {
                            return
                        }

                        // Map the first shared element name to the child ImageView.
                        sharedElements
                                .put(names[0], selectedViewHolder.itemView.findViewById(R.id.card_image))
                    }
                })
    }
}
