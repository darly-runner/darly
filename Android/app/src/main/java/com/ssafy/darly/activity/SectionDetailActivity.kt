package com.ssafy.darly.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.darly.R
import com.ssafy.darly.adapter.record.SectionDetailListAdapter
import com.ssafy.darly.adapter.record.SectionListAdapter
import com.ssafy.darly.databinding.ActivitySectionDetailBinding
import com.ssafy.darly.model.record.SectionString
import com.ssafy.darly.viewmodel.SectionViewModel

class SectionDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySectionDetailBinding
    private lateinit var sectionDetailListAdapter: SectionDetailListAdapter
    private val model: SectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_detail)

        binding.lifecycleOwner = this
        binding.viewModel = model

        sectionDetailListAdapter = SectionDetailListAdapter()
        binding.recyclerView.adapter = sectionDetailListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        model.sections.value = intent.getSerializableExtra("sectionList") as List<SectionString>
        model.gapSectionValue.value = intent.getIntExtra("gapSectionValue", 0)
        model.minSectionIndex.value = intent.getIntExtra("minSectionIndex", 0)
        model.minSectionValue.value = intent.getIntExtra("minSectionValue", 0)

    }
}