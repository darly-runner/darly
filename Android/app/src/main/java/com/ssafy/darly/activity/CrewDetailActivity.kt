package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.databinding.ActivityCrewDetailBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.CrewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrewDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrewDetailBinding
    private val model: CrewViewModel by viewModels()
    var crewName: String=""
    var crewLocation: String=""
    var crewPeopleNum: Long=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_crew_detail)
        binding.lifecycleOwner = this
        binding.viewModel = model

        val glide = Glide.with(this)
        val crewId = intent.getIntExtra("crewId", 0).toLong()
        Log.d("activiey crewId", crewId.toString())
        CoroutineScope(Dispatchers.Main).launch {
//            val crewId = intent.getLongExtra("crewId", -1)
//            Log.d("activiey crewId", crewId.toString())
            val response = DarlyService.getDarlyService().getCrewDetail(crewId = crewId)
            Log.d("check", "${response.body()}")
//            crewName = response.body()?.crewName ?:

            binding.crewName.text = response.body()?.crewName ?: "crewName"
            binding.crewLocation.text = response.body()?.crewLocation ?: "crewLocation"
            binding.crewMembers.text = response.body()?.crewPeople.toString()
            binding.crewDesc.text = response.body()?.crewDesc
            glide.load(response.body()?.crewImage).into(binding.crewImage)
        }
    }
}