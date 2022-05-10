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
import com.ssafy.darly.adapter.crew.CrewMatchListAdapter
import com.ssafy.darly.databinding.FragmentCrewDataBinding
import com.ssafy.darly.databinding.FragmentCrewMatchBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.CrewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrewMatchFragment : Fragment() {
    private lateinit var binding: FragmentCrewMatchBinding
    private val model: CrewViewModel by viewModels()
    var crewId: Long=0
    lateinit var adapter: CrewMatchListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_crew_match, container, false)
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
        val glide = Glide.with(this@CrewMatchFragment)

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getRoomsList(crewId = crewId, page=0, size=50)
            Log.d("match", "${response.body()}")
            model.crewRoomsList.value = response.body()?.matches

            val roomsList = model.crewRoomsList.value
            adapter = CrewMatchListAdapter(
                roomsList!!,
                LayoutInflater.from(context),
                glide
            )
            binding.crewRoomsList.adapter = adapter
            binding.crewRoomsList.layoutManager = GridLayoutManager(context, 1)
        }
    }
}