package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.ssafy.darly.R
import com.ssafy.darly.adapter.match.MatchViewPagerAdapter
import com.ssafy.darly.databinding.ActivityMatchBinding
import com.ssafy.darly.model.CompetitorInfo
import com.ssafy.darly.model.RecordRequest

import com.ssafy.darly.viewmodel.RunningViewModel

class MatchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchBinding
    private val model : RunningViewModel by viewModels()

    private val adapter = MatchViewPagerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_match)
        binding.lifecycleOwner = this
        binding.viewModel = model

        init()
    }

    fun init(){
        binding.matchViewPager.adapter = adapter

        val list = mutableListOf<CompetitorInfo>()

        for(i in 0 until 3){
            list.add(CompetitorInfo(
                RecordRequest(
                    null,
                    0f,
                    0,
                    0,
                    0,
                    0f,
                    0,
                    null,
                    null,
                    ArrayList(),
                    ArrayList(),
                    ArrayList()
                )))
        }
        adapter.list = list
        adapter.notifyDataSetChanged()
    }
}