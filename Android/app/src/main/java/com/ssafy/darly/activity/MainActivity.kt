package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ssafy.darly.R
import com.ssafy.darly.viewmodel.MainViewModel
import com.ssafy.darly.databinding.ActivityMainBinding

class MainActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()

    private val HOME_FRAGMENT = "home_fragment"
    private val SURVEY_FRAGMENT = "survey_fragment"
    private val MYPAGE_FRAGMENT = "mypage_fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = model

        setBottomNavigationBar()
    }

    // OnNavigationItemSelectedListener를 통해 탭 아이템 선택 시 이벤트를 처리
    fun setBottomNavigationBar(){
        // navi_menu.xml 에서 설정했던 각 아이템들의 id를 통해 알맞은 프래그먼트로 변경하게 한다.
        binding.bnvMain.run { setOnItemSelectedListener  {
            when(it.itemId) {
                R.id.first -> {
                    //setFragment(HOME_FRAGMENT, HomeFragment())
                    binding.bnvMain.itemIconTintList = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    binding.bnvMain.itemTextColor = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    true
                }
                R.id.second -> {
                    //setFragment(SURVEY_FRAGMENT, SurveyFragment())
                    binding.bnvMain.itemIconTintList = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    binding.bnvMain.itemTextColor = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    true
                }
                R.id.third -> {
                    //setFragment(MYPAGE_FRAGMENT, MypageFragment())
                    binding.bnvMain.itemIconTintList = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    binding.bnvMain.itemTextColor = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    true
                }
                R.id.fourth -> {
                    //setFragment(MYPAGE_FRAGMENT, MypageFragment())
                    binding.bnvMain.itemIconTintList = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    binding.bnvMain.itemTextColor = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    true
                }
                R.id.fifth -> {
                    //setFragment(MYPAGE_FRAGMENT, MypageFragment())
                    binding.bnvMain.itemIconTintList = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    binding.bnvMain.itemTextColor = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    true
                }
                else -> false
            }
        }
            selectedItemId = intent.getIntExtra("backFragment",R.id.first) // 앱 실행시 보일 fragment
        }
    }

    fun setFragment(tag: String, fragment: Fragment){
        val manager: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null) {
            ft.add(R.id.fl_container, fragment, tag)
        }

        val home = manager.findFragmentByTag(HOME_FRAGMENT)
        val survey = manager.findFragmentByTag(SURVEY_FRAGMENT)
        val mypage = manager.findFragmentByTag(MYPAGE_FRAGMENT)

        // Hide all Fragment
        if (home != null) {
            ft.hide(home)
        }
        if (survey != null) {
            ft.hide(survey)
        }
        if (mypage != null) {
            ft.hide(mypage)
        }

        // Show  current Fragment
        if (tag == HOME_FRAGMENT) {
            if (home != null) {
                ft.show(home)
            }
        }
        if (tag == SURVEY_FRAGMENT) {
            if (survey != null) {
                ft.show(survey)
            }
        }
        if (tag == MYPAGE_FRAGMENT) {
            if (mypage != null) {
                ft.show(mypage)
            }
        }
        ft.commitAllowingStateLoss()
    }
}