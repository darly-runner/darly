package com.ssafy.darly.adapter.crew

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ssafy.darly.fragment.CrewDataFragment
import com.ssafy.darly.fragment.CrewFeedFragment
import com.ssafy.darly.fragment.CrewMatchFragment

class CrewDetailFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return CrewDataFragment()
            1 -> return CrewFeedFragment()
            2 -> return CrewMatchFragment()
        }
        return CrewDataFragment()
    }
//        val fragment = when(position) {
//            0 -> CrewDataFragment().newInstant()
//            1 -> CrewFeedFragment().newInstant()
//            2 -> CrewMatchFragment().newInstant()
//        }
//        return fragment
}