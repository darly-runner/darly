package com.ssafy.darly.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ssafy.darly.fragment.MultiFragment
import com.ssafy.darly.fragment.PauseFragment
import com.ssafy.darly.fragment.RunningFragment
import com.ssafy.darly.fragment.SoloFragment

class RunningViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0       ->  RunningFragment()
            else    ->  PauseFragment()
        }

    }
    // 생성 할 Fragment 의 개수
    override fun getCount() = 2

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }
}