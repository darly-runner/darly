package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.adapter.crew.CrewFeedsAdapter
import com.ssafy.darly.databinding.ActivityCrewFeedsDetailBinding
import com.ssafy.darly.databinding.ActivityCrewFeedsDetailBindingImpl
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.CrewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrewFeedsDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrewFeedsDetailBinding
    private val model: CrewViewModel by viewModels()
    lateinit var adapter: CrewFeedsAdapter
    var feedId: Long=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_crew_feeds_detail)
        binding.lifecycleOwner = this
        binding.viewModel = model

        val glide = Glide.with(this)
        feedId = intent.getLongExtra("feedId", 0)

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getFeedsDetail(feedId = feedId)
            
            Log.d("feeed", "${response}")
        }

    }
}