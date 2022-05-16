package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.darly.R
import com.ssafy.darly.adapter.record.AchieveListAdapter
import com.ssafy.darly.databinding.ActivityAchieveDetailBinding
import com.ssafy.darly.model.record.Achieve
import com.ssafy.darly.viewmodel.AchieveViewModel

class AchieveDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAchieveDetailBinding
    private lateinit var achieveListAdapter: AchieveListAdapter
    private val model: AchieveViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_achieve_detail)
        binding.lifecycleOwner = this
        binding.viewModel = model

        achieveListAdapter = AchieveListAdapter()

        binding.recyclerView.adapter = achieveListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.backBtn.setOnClickListener { finish() }

        model.achieves.value = intent.getSerializableExtra("achieveList") as List<Achieve>
        achieveListAdapter.notifyDataSetChanged()
    }
}