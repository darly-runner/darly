package com.ssafy.darly.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.activity.AllCrewListActivity
import com.ssafy.darly.activity.CreateCrewActivity
import com.ssafy.darly.adapter.crew.MyCrewListAdapter
import com.ssafy.darly.adapter.crew.main.CrewRecommendationAdapter
import com.ssafy.darly.databinding.FragmentCrewBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.CrewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrewFragment : Fragment() {
    private lateinit var binding: FragmentCrewBinding
    private val model: CrewViewModel by viewModels()
    lateinit var recAdapter: CrewRecommendationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_crew, container, false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val glide = Glide.with(this@CrewFragment)

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().myCrewList()
            model.myCrewList.value = response.body()?.crew ?: listOf()

            val crewItemList = model.myCrewList.value
            val adapter = MyCrewListAdapter(
                crewItemList!!,
                LayoutInflater.from(context),
                glide
            )
            binding.myCrew.adapter = adapter
            binding.crewRecommendation.layoutManager = GridLayoutManager(context, 2)
        }

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getCrewList(page=0, size = 8, address = 0, key = "" )
            model.crewRecommendationList.value = response.body()?.crew ?: listOf()

            val crewRecommendationList = model.crewRecommendationList.value
            recAdapter = CrewRecommendationAdapter(
                crewRecommendationList!!,
                LayoutInflater.from(context),
                glide
            )
            binding.crewRecommendation.adapter = recAdapter
        }

        // createCrew
        binding.createCrew.setOnClickListener {
            val intent = Intent(context, CreateCrewActivity::class.java)
            startActivity(intent)
        }

        // All crew lists
        binding.searchCrew.setOnClickListener {
            val intent = Intent(context, AllCrewListActivity::class.java)
            startActivity(intent)
        }
    }
}

