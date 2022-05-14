package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
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
    private var feedId: Long=0
    var position: Int = 0
    var crewId: Long=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_crew_feeds_detail)
        binding.lifecycleOwner = this
        binding.viewModel = model

        val glide = Glide.with(this)
        feedId = intent.getLongExtra("feedId", 0)
        crewId = intent.getLongExtra("crewId", 0)
        position = intent.getIntExtra("position", 0)

//        adapter = CrewFeedsAdapter()

        val smoothScroller : RecyclerView.SmoothScroller by lazy {
            object: LinearSmoothScroller(this) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = position
        binding.feedDetails.layoutManager?.startSmoothScroll(smoothScroller)


        CoroutineScope(Dispatchers.Main).launch {
//            val response = DarlyService.getDarlyService().getFeedsDetail(feedId = feedId, crewId = crewId, size=50)
            val response = DarlyService.getDarlyService().getCrewFeeds(crewId = crewId, page = 0, size = 50)
            model.crewDetailFeeds.value = response.body()?.feeds
            adapter = CrewFeedsAdapter(
                model.crewDetailFeeds.value!!,
                LayoutInflater.from(this@CrewFeedsDetailActivity),
                glide
            )
            binding.feedDetails.adapter = adapter
            binding.feedDetails.layoutManager = GridLayoutManager(this@CrewFeedsDetailActivity, 1)

            Log.d("feeed", "${response}")
        }


    }
}