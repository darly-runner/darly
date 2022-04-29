package com.ssafy.darly.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.ssafy.darly.R
import com.ssafy.darly.activity.CreateCrewActivity
import com.ssafy.darly.adapter.MyCrewListAdapter
import com.ssafy.darly.adapter.crew.main.CrewRecommendationAdapter
import com.ssafy.darly.adapter.crew.main.CrewRecommendationAdapterDecoration
import com.ssafy.darly.databinding.FragmentActBinding
import com.ssafy.darly.databinding.FragmentCrewBinding
import com.ssafy.darly.model.MyCrewDetails
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.ActViewModel
import com.ssafy.darly.viewmodel.CrewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrewFragment : Fragment() {
    private lateinit var binding: FragmentCrewBinding
    private val model: CrewViewModel by viewModels()
    lateinit var adapter: MyCrewListAdapter
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

    fun subscribeObservers() {
        model.myCrewList.observe(viewLifecycleOwner, Observer { crewList ->
            adapter.submitList(crewList)
        })
    }

    fun subscribeObserversCrew() {
        model.crewRecommendationList.observe(viewLifecycleOwner, Observer { crewList ->
            recAdapter.submitList(crewList)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        subscribeObserversCrew()

        val glide = Glide.with(this@CrewFragment)
        adapter = MyCrewListAdapter(
            LayoutInflater.from(context),
            glide
        )
        binding.myCrew.adapter = adapter

        binding.crewRecommendation.addItemDecoration(CrewRecommendationAdapterDecoration())
        recAdapter = CrewRecommendationAdapter(
            LayoutInflater.from(context),
            glide
        )
        binding.crewRecommendation.adapter = recAdapter

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().myCrewList()
            model.myCrewList.value = response.body()?.crew ?: listOf()

            Log.d("Crew List", "${response}")
            Log.d("Crew List 2", "${response.body()}")
        }

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getCrewList(page=0, size = 8, address = 0, key = "" )
            model.crewRecommendationList.value = response.body()?.crew ?: listOf()

            Log.d("Crew Recommendation", "${response}")
            Log.d("Crew Recommendation", "${response.body()}")
        }

        // createCrew
        binding.createCrew.setOnClickListener {
            val intent = Intent(context, CreateCrewActivity::class.java)
            startActivity(intent)
        }
    }
}

