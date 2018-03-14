package com.project.jeremyg.myinstagram.views.adapters

import android.support.v4.app.Fragment
import com.project.jeremyg.myinstagram.views.fragments.ImageFragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import com.project.jeremyg.myinstagram.view_models.PostsListViewModel
import com.project.jeremyg.myinstagram.views.fragments.ImagePagerFragment

class ImagePagerAdapter(fragment: Fragment, var postsListViewModel: PostsListViewModel)
    : FragmentStatePagerAdapter(fragment.getChildFragmentManager()) {

    private val TAG = ImagePagerFragment::class.java.canonicalName

    override fun getCount(): Int {
        Log.e(TAG, "getCount()" + postsListViewModel.getPostsSize())
        return postsListViewModel.getPostsSize()
    }

    override fun getItem(position: Int): Fragment {
        Log.e(TAG, "getItem(" + position + ")")
        return ImageFragment.newInstance(postsListViewModel.getPostAt(position))
    }
}