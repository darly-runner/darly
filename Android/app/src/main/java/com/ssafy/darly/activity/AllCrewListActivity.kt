package com.ssafy.darly.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.adapter.crew.main.CrewRecommendationAdapter
import com.ssafy.darly.databinding.ActivityAllCrewListBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.CrewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllCrewListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllCrewListBinding
    lateinit var adapter: CrewRecommendationAdapter
    private val model: CrewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_crew_list)
        binding.lifecycleOwner = this
        binding.viewModel = model

        val glide = Glide.with(this)
//        adapter = CrewRecommendationAdapter(
//            LayoutInflater.from(this),
//            glide
//        )
//        binding.crewList.adapter = adapter

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getCrewList(page=0, size = 20, address = 0, key = "" )
            model.crewRecommendationList.value = response.body()?.crew ?: listOf()

            val crewRecommendationList = model.crewRecommendationList.value
            adapter = CrewRecommendationAdapter(
                crewRecommendationList!!,
                LayoutInflater.from(this@AllCrewListActivity),
                glide
            )
            binding.crewList.adapter = adapter
            binding.crewList.layoutManager = GridLayoutManager(this@AllCrewListActivity, 2)
        }

//        binding.crewList.layoutManager = GridLayoutManager(this, 2)
//        crewRecommendationAdapter = CrewRecommendationAdapter()
//        binding.recyclerView.adapter =
    }

//    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
//        return super.onCreateView(name, context, attrs)
//    }
}