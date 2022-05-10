package com.ssafy.darly.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ssafy.darly.fragment.MultiFragment
import com.ssafy.darly.fragment.SoloFragment

class ViewPagerAdapter(fm: FragmentManager,ac : String) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0       ->  SoloFragment()
            else    ->  MultiFragment()
        }

    }
    // 생성 할 Fragment 의 개수
    override fun getCount() = 2

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }
}