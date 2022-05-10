package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.darly.R
import com.ssafy.darly.adapter.record.RecordListAdapter
import com.ssafy.darly.databinding.ActivityRecordAllBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.RecordViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordAllActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordAllBinding
    private lateinit var recordListAdapter: RecordListAdapter
    private val model: RecordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_record_all)
        binding.lifecycleOwner = this
        binding.viewModel = model
        recordListAdapter = RecordListAdapter()

        binding.recyclerView.adapter = recordListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.backBtn.setOnClickListener { finish() }

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getRecordList("all")
            model.records.value = response.body()?.records ?: listOf()
            recordListAdapter.notifyDataSetChanged()
        }
    }
}