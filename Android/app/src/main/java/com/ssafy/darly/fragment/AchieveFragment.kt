package com.ssafy.darly.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.darly.R
import com.ssafy.darly.activity.AchieveDetailActivity
import com.ssafy.darly.activity.SectionDetailActivity
import com.ssafy.darly.adapter.record.AchieveListAdapter
import com.ssafy.darly.databinding.FragmentAchieveBinding
import com.ssafy.darly.model.record.Achieve
import com.ssafy.darly.model.record.SectionString
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.AchieveViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AchieveFragment : Fragment() {
    private lateinit var binding: FragmentAchieveBinding
    private lateinit var achieveListAdapter: AchieveListAdapter
    private val model: AchieveViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_achieve, container, false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }
        achieveListAdapter = AchieveListAdapter()
        binding.recyclerView.adapter = achieveListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.allBtn.setOnClickListener {
            val intent = Intent(this.requireContext(), AchieveDetailActivity::class.java)
            val list = arrayListOf<Achieve>()
            model.achieves.value?.let { it1 -> list.addAll(it1) }
            intent.putExtra("achieveList", list)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getUserBadges()
            val list = mutableListOf<Achieve>()
            for (badge in response.body()?.badgeList ?: listOf()) {
                list.add(badge)
                if (list.size == 3)
                    break
            }
            model.achieves.value = response.body()?.badgeList ?: listOf()
            model.shortAchieves.value = list
            achieveListAdapter.notifyDataSetChanged()
        }
    }
}