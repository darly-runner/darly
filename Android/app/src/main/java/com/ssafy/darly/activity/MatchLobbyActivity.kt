package com.ssafy.darly.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
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


//        adapter = CrewMatchLobbyAdapter(
////            model.matchUsers.value!!,
//            LayoutInflater.from(this),
//            glide
//        )
//        binding.matchUsersList.adapter = adapter
//        binding.matchUsersList.layoutManager = GridLayoutManager(this, 1)
//        subscribeObserver()

        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getMatchDetails(matchId)
            model.matchUsers.value = response.body()?.users ?: listOf()

            binding.matchTitle.text = response.body()?.matchTitle ?: ""
//            binding.hostNickname.text = response.body()?.
            binding.goalDistance.text = response.body()?.matchGoalDistance.toString()
            binding.currentNum.text = response.body()?.matchCurPerson.toString()
            Log.d("matchId", "${response.body()}")
            Log.d("qiqiqiqi", "${model.matchUsers.value}")

            adapter = CrewMatchLobbyAdapter(
                model.matchUsers.value!!,
                LayoutInflater.from(this@MatchLobbyActivity),
                glide
            )
            binding.matchUsersList.adapter = adapter
            binding.matchUsersList.layoutManager = GridLayoutManager(this@MatchLobbyActivity, 1)
        }
    }

//    fun subscribeObserver() {
//        model.matchUsers.observe(this, Observer {
//            adapter.users = model.matchUsers.value as MutableList<MatchUsers>
//        })
//    }
}