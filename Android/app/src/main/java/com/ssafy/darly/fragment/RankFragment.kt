package com.ssafy.darly.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.darly.R
import com.ssafy.darly.adapter.record.RankListAdapter
import com.ssafy.darly.databinding.FragmentRankBinding
import com.ssafy.darly.viewmodel.RecordDetailViewModel

class RankFragment : Fragment() {
    private lateinit var binding: FragmentRankBinding
    private lateinit var rankListAdapter: RankListAdapter
    private val model: RecordDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rank, container, false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }

        rankListAdapter = RankListAdapter()
        binding.recyclerView.adapter = rankListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        subscribeObserver()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //coroutine
    }

    private fun subscribeObserver() {
        model.rankStringList.observe(viewLifecycleOwner, Observer {
            if (model.rankStringList.value?.size ?: 0 > 0) {
                rankListAdapter.notifyDataSetChanged()
                binding.layout.visibility = View.VISIBLE
            }
        })
    }
}