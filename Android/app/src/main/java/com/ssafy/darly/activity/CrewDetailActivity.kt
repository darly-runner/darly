package com.ssafy.darly.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
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
    var crewId: Long = 0
    var crewJoin: String = ""
    var prevCrewJoin: String =""
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
        crewId = intent.getLongExtra("crewId", 0)
        model.crewId.value = crewId

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getCrewDetail(crewId = crewId)

            binding.crewName.text = response.body()?.crewName ?: "crewName"
            binding.crewLocation.text = response.body()?.crewLocation ?: "crewLocation"
            binding.crewMembers.text = response.body()?.crewPeople.toString()
            binding.crewDesc.text = response.body()?.crewDesc
            glide.load(response.body()?.crewImage).into(binding.crewImage)

            prevCrewJoin = response.body()?.crewStatus ?: "N"

            when (prevCrewJoin) {
                "A" -> {
                    binding.crewJoinButton.setBackgroundResource(R.drawable.button_crewjoin_disable)
                    binding.crewJoinButton.setTextColor(Color.rgb(114, 87, 93))
                    binding.crewJoinButton.text = "승인 대기중"
                }
                "J" -> binding.crewJoinButton.visibility = View.GONE
                "N" -> {
                    binding.crewJoinButton.setOnClickListener {
                        CoroutineScope(Dispatchers.Main).launch {
                            val joinResponse = DarlyService.getDarlyService().crewJoin(crewId)
                            Log.d("Join", "${joinResponse.body()}")
                            val intent = Intent(this@CrewDetailActivity, CrewDetailActivity::class.java)
                            intent.putExtra("crewId", crewId)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP //액티비티 스택제거
//                            intent.putExtra("crewId", crewId)
                            ContextCompat.startActivity(this@CrewDetailActivity, intent, null)
                        }
                    }
                }
            }
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

    fun getCrewStatus(): String {
        return crewJoin
    }
}