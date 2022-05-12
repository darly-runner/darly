package com.ssafy.darly.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.darly.R
import com.ssafy.darly.adapter.record.SectionListAdapter
import com.ssafy.darly.databinding.FragmentSectionBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.RecordDetailViewModel
import com.ssafy.darly.viewmodel.RunningViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SectionFragment() : Fragment() {
    private lateinit var binding: FragmentSectionBinding
    private lateinit var sectionListAdapter: SectionListAdapter
    private lateinit var model: RecordDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_section, container, false)
        activity?.let {
            binding.lifecycleOwner = this
            model = ViewModelProvider(it, ViewModelProvider.NewInstanceFactory())[RecordDetailViewModel::class.java]
            binding.viewModel = model
        }

        sectionListAdapter = SectionListAdapter()
        binding.recyclerView.adapter = sectionListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sectionListAdapter.notifyDataSetChanged()
        Log.d("response--", "${model.sections.value}")
    }
}