package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.adapter.crew.main.CrewRecommendationAdapter
import com.ssafy.darly.databinding.ActivityAllCrewListBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.CrewViewModel
import com.ssafy.darly.websocket.WebSocketListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class AllCrewListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllCrewListBinding
    lateinit var adapter: CrewRecommendationAdapter
    private val model: CrewViewModel by viewModels()
    var crewName: String =""

//    private lateinit var client: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_crew_list)
        binding.lifecycleOwner = this
        binding.viewModel = model

        val glide = Glide.with(this)

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getCrewList(page=0, size = 50, address = 0, key = "" )
            model.crewRecommendationList.value = response.body()?.crew ?: listOf()

            val crewRecommendationList = model.crewRecommendationList.value
            adapter = CrewRecommendationAdapter(
                crewRecommendationList!!,
                LayoutInflater.from(this@AllCrewListActivity),
                glide
            )
            binding.crewList.adapter = adapter
            binding.crewList.layoutManager = GridLayoutManager(this@AllCrewListActivity, 2)
        }

        binding.searchCrew.doAfterTextChanged {
            crewName = it.toString()
            Log.d("search name", crewName)
            CoroutineScope(Dispatchers.Main).launch {
                val response = DarlyService.getDarlyService().getCrewList(page=0, size = 50, address = 0, key = crewName )
                model.crewRecommendationList.value = response.body()?.crew ?: listOf()

                val crewRecommendationList = model.crewRecommendationList.value
                adapter = CrewRecommendationAdapter(
                    crewRecommendationList!!,
                    LayoutInflater.from(this@AllCrewListActivity),
                    glide
                )
                binding.crewList.adapter = adapter
                binding.crewList.layoutManager = GridLayoutManager(this@AllCrewListActivity, 2)
            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

//        client = OkHttpClient()
//
//        val request: Request = Request.Builder().url("ws://3.36.61.107:80/ws").build()
//        val listener: WebSocketListener = WebSocketListener()
//
//        client.newWebSocket(request, listener)
//        client.dispatcher().executorService().shutdown()
    }
}