package com.ssafy.darly.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.activity.CrewDetailActivity
import com.ssafy.darly.adapter.crew.CrewDetailRankAdapter
import com.ssafy.darly.databinding.FragmentCrewDataBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.CrewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrewDataFragment : Fragment() {
    private lateinit var binding: FragmentCrewDataBinding
    private val model: CrewViewModel by viewModels()
    var crewId: Long = 0
    lateinit var adapter: CrewDetailRankAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_crew_data, container, false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        crewId = (activity as CrewDetailActivity).getCrewId()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val glide = Glide.with(this@CrewDataFragment)

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getCrewSummary(crewId = crewId, type = "week")
            model.crewDetailRankings.value = response.body()?.ranks

            binding.crewDistance.text = response.body()?.crewDistance.toString()
            binding.crewDetailPace.text = response.body()?.crewPace.toString()
            binding.crewDetailPplNum.text = response.body()?.crewPeopleNum.toString()
            binding.crewDetailTime.text = response.body()?.crewTime.toString()

            val crewRankingsList = model.crewDetailRankings.value
            adapter = CrewDetailRankAdapter(
                crewRankingsList!!,
                LayoutInflater.from(context),
                glide
            )
            binding.crewRankingList.adapter = adapter
            binding.crewRankingList.layoutManager = GridLayoutManager(context, 1)
        }
    }
}