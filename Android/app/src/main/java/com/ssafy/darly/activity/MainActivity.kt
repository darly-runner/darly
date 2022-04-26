package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ssafy.darly.R
import com.ssafy.darly.viewmodel.MainViewModel
import com.ssafy.darly.databinding.ActivityMainBinding
import com.ssafy.darly.fragment.*
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.util.GlobalApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()

    private val RUNNING_FRAGMENT = "running_fragment"
    private val CREW_FRAGMENT = "crew_fragment"
    private val ACT_FRAGMENT = "act_fragment"
    private val CHALLENGE_FRAGMENT = "challenge_fragment"
    private val MYPAGE_FRAGMENT = "mypage_fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = model

        CoroutineScope(Dispatchers.IO).launch {
            val response = DarlyService.getDarlyService().getUsers(1)
            Log.d("MainActivity", "${response}")
        }

        setBottomNavigationBar()
    }

    private fun setBottomNavigationBar(){
        binding.bnvMain.run { setOnItemSelectedListener  {
            when(it.itemId) {
                R.id.first -> {
                    setFragment(RUNNING_FRAGMENT, RunningFragment())
                    binding.bnvMain.itemIconTintList = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    binding.bnvMain.itemTextColor = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    true
                }
                R.id.second -> {
                    setFragment(CREW_FRAGMENT, CrewFragment())
                    binding.bnvMain.itemIconTintList = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    binding.bnvMain.itemTextColor = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    true
                }
                R.id.third -> {
                    setFragment(ACT_FRAGMENT, ActFragment())
                    binding.bnvMain.itemIconTintList = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    binding.bnvMain.itemTextColor = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    true
                }
                R.id.fourth -> {
                    setFragment(CHALLENGE_FRAGMENT, ChallengeFragment())
                    binding.bnvMain.itemIconTintList = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    binding.bnvMain.itemTextColor = ContextCompat.getColorStateList(context, R.color.color_bnv)
                    true
                }
                R.id.fifth -> {
                    setFragment(MYPAGE_FRAGMENT, MypageFragment())
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

    private fun setFragment(tag: String, fragment: Fragment){
        val manager: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null) {
            ft.add(R.id.fl_container, fragment, tag)
        }

        val running = manager.findFragmentByTag(RUNNING_FRAGMENT)
        val crew = manager.findFragmentByTag(CREW_FRAGMENT)
        val act = manager.findFragmentByTag(ACT_FRAGMENT)
        val challenge = manager.findFragmentByTag(CHALLENGE_FRAGMENT)
        val mypage = manager.findFragmentByTag(MYPAGE_FRAGMENT)

        // Hide all Fragment
        if (running != null) {
            ft.hide(running)
        }
        if (crew != null) {
            ft.hide(crew)
        }
        if (act != null) {
            ft.hide(act)
        }
        if (challenge != null) {
            ft.hide(challenge)
        }
        if (mypage != null) {
            ft.hide(mypage)
        }

        // Show  current Fragment
        if (tag == RUNNING_FRAGMENT) {
            if (running != null) {
                ft.show(running)
            }
        }
        if (tag == CREW_FRAGMENT) {
            if (crew != null) {
                ft.show(crew)
            }
        }
        if (tag == ACT_FRAGMENT) {
            if (act != null) {
                ft.show(act)
            }
        }
        if (tag == CHALLENGE_FRAGMENT) {
            if (challenge != null) {
                ft.show(challenge)
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