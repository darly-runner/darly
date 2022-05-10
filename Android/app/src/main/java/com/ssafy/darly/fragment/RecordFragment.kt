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
import com.ssafy.darly.activity.FriendActivity
import com.ssafy.darly.activity.RecordAllActivity
import com.ssafy.darly.adapter.record.RecordListAdapter
import com.ssafy.darly.databinding.FragmentRecordBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.RecordViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordFragment : Fragment() {
    private lateinit var binding: FragmentRecordBinding
    private lateinit var recordListAdapter: RecordListAdapter
    private val model: RecordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_record, container, false)
        activity?.let {
            binding.lifecycleOwner = this
            binding.viewModel = model
        }
        recordListAdapter = RecordListAdapter(requireActivity())
        binding.recyclerView.adapter = recordListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.allBtn.setOnClickListener {
            val intent = Intent(this.requireContext(), RecordAllActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getRecordList("top")
            model.records.value = response.body()?.records ?: listOf()
            recordListAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getRecordList("top")
            model.records.value = response.body()?.records ?: listOf()
            recordListAdapter.notifyDataSetChanged()
        }
    }
}