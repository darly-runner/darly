package com.ssafy.darly.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.darly.R
import com.ssafy.darly.activity.RecordAllActivity
import com.ssafy.darly.activity.SectionDetailActivity
import com.ssafy.darly.adapter.record.SectionListAdapter
import com.ssafy.darly.databinding.FragmentSectionBinding
import com.ssafy.darly.model.record.SectionString
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.RecordDetailViewModel
import com.ssafy.darly.viewmodel.RunningViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SectionFragment : Fragment() {
    private lateinit var binding: FragmentSectionBinding
    private lateinit var sectionListAdapter: SectionListAdapter
    private val model: RecordDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_section, container, false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }

        sectionListAdapter = SectionListAdapter()
        binding.recyclerView.adapter = sectionListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.allBtn.setOnClickListener {
            val intent = Intent(this.requireContext(), SectionDetailActivity::class.java)
            val list = arrayListOf<SectionString>()
            model.sections.value?.let { it1 -> list.addAll(it1) }
            intent.putExtra("sectionList", list)
            intent.putExtra("gapSectionValue", model.gapSectionValue.value)
            intent.putExtra("minSectionIndex", model.minSectionIndex.value)
            intent.putExtra("minSectionValue", model.minSectionValue.value)
            startActivity(intent)
        }

        subscribeObserver()

        return binding.root
    }

    private fun subscribeObserver(){
        model.sections.observe(viewLifecycleOwner, Observer {
            sectionListAdapter.notifyDataSetChanged()
        })
    }
}