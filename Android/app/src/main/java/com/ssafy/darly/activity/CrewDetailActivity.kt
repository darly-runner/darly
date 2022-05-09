package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.darly.R
import com.ssafy.darly.adapter.crew.CrewDetailFragmentAdapter
import com.ssafy.darly.databinding.ActivityCrewDetailBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.CrewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrewDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrewDetailBinding
    private val model: CrewViewModel by viewModels()
    var crewId:Long=0
    private val tabTitleArray = arrayOf(
        "요약",
        "피드",
        "경쟁"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_crew_detail)
        binding.lifecycleOwner = this
        binding.viewModel = model

        val glide = Glide.with(this)
        crewId = intent.getIntExtra("crewId", 0).toLong()
        model.crewId.value = crewId
        Log.d("activieyyy crewId", model.crewId.value.toString())
        CoroutineScope(Dispatchers.Main).launch {

            val response = DarlyService.getDarlyService().getCrewDetail(crewId = crewId)

            binding.crewName.text = response.body()?.crewName ?: "crewName"
            binding.crewLocation.text = response.body()?.crewLocation ?: "crewLocation"
            binding.crewMembers.text = response.body()?.crewPeople.toString()
            binding.crewDesc.text = response.body()?.crewDesc
            glide.load(response.body()?.crewImage).into(binding.crewImage)
        }

        val viewPager = binding.crewDetailViewPager
        val tabLayout = binding.crewDetailTab

        viewPager.adapter = CrewDetailFragmentAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }

    @JvmName("getCrewId1")
    fun getCrewId(): Long {
        return crewId
    }
}