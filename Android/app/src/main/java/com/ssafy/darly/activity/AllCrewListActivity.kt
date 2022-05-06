package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.ssafy.darly.R
import com.ssafy.darly.adapter.crew.main.CrewRecommendationAdapter
import com.ssafy.darly.databinding.ActivityAllCrewListBinding
import com.ssafy.darly.viewmodel.CrewViewModel

class AllCrewListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllCrewListBinding
    private lateinit var crewRecommendationAdapter: CrewRecommendationAdapter
    private val model: CrewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_crew_list)
        binding.lifecycleOwner = this
        binding.viewModel = model
        crewRecommendationAdapter = CrewRecommendationAdapter()
//        binding.recyclerView.adapter =
    }
}