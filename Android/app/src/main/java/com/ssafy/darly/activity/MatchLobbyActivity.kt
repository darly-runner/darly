package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.adapter.CrewMatchLobbyAdapter
import com.ssafy.darly.databinding.ActivityMatchLobbyBinding
import com.ssafy.darly.model.MatchUsers
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.CrewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MatchLobbyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchLobbyBinding
    private val model: CrewViewModel by viewModels()
    var matchId: Long = 0
    lateinit var adapter: CrewMatchLobbyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_match_lobby)
        binding.lifecycleOwner = this
        binding.viewModel = model

        val glide = Glide.with(this)
        matchId = intent.getLongExtra("matchId", 0)
        subscribeObserver()

        adapter = CrewMatchLobbyAdapter(
//            model.matchUsers.value!!,
            LayoutInflater.from(this),
            glide
        )
        binding.matchUsersList.adapter = adapter

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getMatchDetails(matchId)
            model.matchUsers.value = response.body()?.users ?: listOf()
            Log.d("matchId", "${response.body()}")

//            adapter = CrewMatchLobbyAdapter(
//                model.matchUsers.value!!,
//                LayoutInflater.from(this@MatchLobbyActivity),
//                glide
//            )
//            binding.matchUsersList.adapter = adapter
        }
    }

    fun subscribeObserver() {
        model.matchUsers.observe(this, Observer {
            adapter.users = model.matchUsers.value as MutableList<MatchUsers>
        })
    }
}