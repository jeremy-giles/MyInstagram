package com.project.jeremyg.myinstagram.views.adapters

import android.support.v7.widget.RecyclerView
import com.project.jeremyg.myinstagram.view_models.PostsListViewModel
import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.project.jeremyg.myinstagram.views.fragments.ImagePagerFragment
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.load.DataSource
import com.project.jeremyg.myinstagram.R
import com.project.jeremyg.myinstagram.views.activities.UserActivity
import java.util.concurrent.atomic.AtomicBoolean
import com.bumptech.glide.Glide

class GridAdapter() : RecyclerView.Adapter<GridAdapter.PostViewHolder>() {

    /**
     * A listener that is attached to all ViewHolders to handle image loading events and clicks.
     */
    interface ViewHolderListener {

        fun onLoadCompleted(view: ImageView, adapterPosition: Int)

        fun onItemClicked(view: View, adapterPosition: Int)
    }

    private lateinit var requestManager: RequestManager
    private lateinit var viewHolderListener: ViewHolderListener
    private lateinit var postsViewModel: PostsListViewModel

    /**
     * Constructs a new grid adapter for the given [Fragment].
     */
    constructor(fragment: Fragment, postsViewModel: PostsListViewModel?) : this() {
        this.requestManager = Glide.with(fragment)
        this.viewHolderListener = ViewHolderListenerImpl(fragment)
        this.postsViewModel = postsViewModel!!
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.onBind(postsViewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.image_card, parent, false),
                requestManager, viewHolderListener)
    }

    override fun getItemCount(): Int {
        return postsViewModel.getPostsSize()
    }

    /**
     * Default [ViewHolderListener] implementation.
     */
    private class ViewHolderListenerImpl internal constructor(private val fragment: Fragment) : ViewHolderListener {
        private val enterTransitionStarted: AtomicBoolean

        init {
            this.enterTransitionStarted = AtomicBoolean()
        }

        override fun onLoadCompleted(view: ImageView, position: Int) {
            // Call startPostponedEnterTransition only when the 'selected' image loading is completed.
            if (UserActivity.currentPosition !== position) {
                return
            }
            if (enterTransitionStarted.getAndSet(true)) {
                return
            }
            fragment.startPostponedEnterTransition()
        }

        /**
         * Handles a view click by setting the current position to the given `position` and
         * starting a [ImagePagerFragment] which displays the image at the position.
         *
         * @param view the clicked [ImageView] (the shared element view will be re-mapped at the
         * GridFragment's SharedElementCallback)
         * @param position the selected view position
         */
        override fun onItemClicked(view: View, position: Int) {
            // Update the position.
            UserActivity.currentPosition = position

            // Exclude the clicked card from the exit transition (e.g. the card will disappear immediately
            // instead of fading out with the rest to prevent an overlapping animation of fade and move).
            (fragment.getExitTransition() as TransitionSet).excludeTarget(view, true)

            val transitioningView = view.findViewById<ImageView>(R.id.card_image)
            fragment.getFragmentManager()?.beginTransaction()?.setReorderingAllowed(true) // Optimize for shared element transition
                    ?.addSharedElement(transitioningView, transitioningView.getTransitionName())
                    ?.replace(R.id.fragment_container, ImagePagerFragment(), ImagePagerFragment::class.java
                            .simpleName)
                    ?.addToBackStack(null)
                    ?.commit()
        }
    }


    /**
     * ViewHolder for the grid's images.
     */
    class PostViewHolder(itemView: View, private val requestManager: RequestManager,
                         val viewHolderListener: ViewHolderListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val image: ImageView

        init {
            this.image = itemView.findViewById(R.id.card_image)
            itemView.findViewById<CardView>(R.id.card_view).setOnClickListener(this)
        }

        /**
         * Binds this view holder to the given adapter position.
         *
         * The binding will load the image into the image view, as well as set its transition name for
         * later.
         */
        fun onBind(postsViewModel: PostsListViewModel) {

            postsViewModel.getPostAt(adapterPosition)?.let {
                setImage(it.images.first().url)
                // Set the string value of the image resource as the unique transition name for the view.
                image.setTransitionName(it.id)
            }

        }

        fun setImage(url: String) {
            // Load the image with Glide to prevent OOM error when the image drawables are very large.
            requestManager
                    .load(url)
                    .listener(object : RequestListener<Drawable> {

                        override fun onLoadFailed(e: GlideException?, model: Any?,
                                                  target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            viewHolderListener.onLoadCompleted(image, adapterPosition)
                            return false
                        }

                        override fun onResourceReady(resource: Drawable, model: Any, target: com.bumptech.glide.request.target.Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                            viewHolderListener.onLoadCompleted(image, adapterPosition)
                            return false
                        }

                    })
                    .into(image)
        }

        override fun onClick(view: View) {
            // Let the listener start the ImagePagerFragment.
            viewHolderListener.onItemClicked(view, adapterPosition)
        }
    }
}
