package com.project.jeremyg.myinstagram.views.adapters

import android.support.v4.app.Fragment
import com.project.jeremyg.myinstagram.views.fragments.ImageFragment
import android.support.v4.app.FragmentStatePagerAdapter
import com.project.jeremyg.myinstagram.view_models.PostsListViewModel

class ImagePagerAdapter(fragment: Fragment, var postsListViewModel: PostsListViewModel)
    : FragmentStatePagerAdapter(fragment.getChildFragmentManager()) {

    override fun getCount(): Int {
        return postsListViewModel.getPostsSize()
    }

    override fun getItem(position: Int): Fragment {
        return ImageFragment.newInstance(postsListViewModel.getPostAt(position))
    }
}