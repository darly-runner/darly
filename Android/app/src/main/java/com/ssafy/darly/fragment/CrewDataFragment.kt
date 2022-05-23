package com.ssafy.darly.fragment

import android.content.Context
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
    private var crewId: Long = 0
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
            val response =
                DarlyService.getDarlyService().getCrewSummary(crewId = crewId, type = "week")
            model.crewDetailRankings.value = response.body()?.ranks

            binding.crewDistance.text = response.body()?.let { String.format("%.02f", it.crewDistance) }
            binding.crewDetailPace.text = response.body()?.crewPace?.toInt()?.let { timeToStr(it) }
            binding.crewDetailPplNum.text = response.body()?.crewPeopleNum.toString()
            binding.crewDetailTime.text = response.body()?.crewTime?.toInt()?.let { timeToStr(it) }

            adapter = CrewDetailRankAdapter(
                model.crewDetailRankings.value!!,
                LayoutInflater.from(context),
                glide
            )
            binding.crewRankingList.adapter = adapter
            binding.crewRankingList.layoutManager = GridLayoutManager(context, 1)
        }
    }

    // time -> hh:mm:ss
    fun timeToStr(t : Int) : String{
        val m = t / 60
        var second = (t % 60).toString()

        val hour = (m / 60).toString()
        var minute = (m % 60).toString()

        if(minute.length == 1)
            minute = "0$minute"

        if(second.length == 1)
            second = "0$second"

        return if(hour != "0")
            "$hour:$minute:$second"
        else
            "$minute:$second"
    }
}