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
import com.ssafy.darly.adapter.crew.CrewDetailFeedsAdapter
import com.ssafy.darly.databinding.FragmentCrewFeedBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.CrewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrewFeedFragment : Fragment() {
    private lateinit var binding: FragmentCrewFeedBinding
    private val model: CrewViewModel by viewModels()
    private var crewId: Long = 0
    private var crewJoin: String = ""
    private var crewName: String = ""
    lateinit var adapter: CrewDetailFeedsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_crew_feed, container, false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        crewId = (activity as CrewDetailActivity).getCrewId()
        crewJoin = (activity as CrewDetailActivity).getCrewStatus()
        crewName = (activity as CrewDetailActivity).getCrewName()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val glide = Glide.with(this@CrewFeedFragment)

        when (crewJoin) {
            "J" -> {
                binding.locked.visibility = View.GONE
                binding.notJoined.visibility = View.GONE
                CoroutineScope(Dispatchers.Main).launch {
                    val response =
                        DarlyService.getDarlyService()
                            .getCrewFeeds(crewId = crewId, page = 0, size = 30)
                    model.crewDetailFeeds.value = response.body()?.feeds

                    val feedsImg = model.crewDetailFeeds.value
                    adapter = CrewDetailFeedsAdapter(
                        feedsImg!!,
                        LayoutInflater.from(context),
                        glide,
                        crewId
                    )
                    binding.crewDetailFeeds.adapter = adapter
                    binding.crewDetailFeeds.layoutManager = GridLayoutManager(context, 3)
                }
            }
            "N" -> {
                binding.line.visibility = View.GONE
                binding.notJoinedCrewName.text = crewName
                binding.crewDetailFeeds.visibility = View.GONE
                binding.noticeLayout.visibility = View.GONE
            }
            "A" -> {
                binding.line.visibility = View.GONE
                binding.notJoinedCrewName.text = crewName
                binding.crewDetailFeeds.visibility = View.GONE
                binding.noticeLayout.visibility = View.GONE
            }
        }
    }
}