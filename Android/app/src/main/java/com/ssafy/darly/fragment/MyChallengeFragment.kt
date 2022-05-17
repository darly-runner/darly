package com.ssafy.darly.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.darly.R
import com.ssafy.darly.adapter.record.MyChallengeListAdapter
import com.ssafy.darly.databinding.FragmentMyChallengeBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.MyChallengeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyChallengeFragment : Fragment() {
    private lateinit var binding: FragmentMyChallengeBinding
    private lateinit var myChallengeListAdapter: MyChallengeListAdapter
    private val model: MyChallengeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_challenge, container, false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }

        myChallengeListAdapter = MyChallengeListAdapter()
        binding.recyclerView.adapter = myChallengeListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getUserEvents()
            model.myChallenges.value = response.body()?.events ?: listOf()
            myChallengeListAdapter.notifyDataSetChanged()
        }
    }
}