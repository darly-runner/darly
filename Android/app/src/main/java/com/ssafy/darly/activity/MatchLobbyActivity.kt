package com.ssafy.darly.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.JsonParser
import com.ssafy.darly.R
import com.ssafy.darly.adapter.CrewMatchLobbyAdapter
import com.ssafy.darly.databinding.ActivityMatchLobbyBinding
import com.ssafy.darly.model.MatchUsers
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.CrewViewModel
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompCommand
import ua.naiksoftware.stomp.dto.StompHeader

class MatchLobbyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchLobbyBinding
    private val model: CrewViewModel by viewModels()
    var matchId: Long = 0
    lateinit var adapter: CrewMatchLobbyAdapter
    var myUserId: Long = 0

    val url = "http://3.36.61.107:8000/ws/websocket"
    val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

    @SuppressLint("CheckResult")
    fun runStomp() {
        val glide = Glide.with(this)
        stompClient.connect()
        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.d("ENTER", "ENTERED!!")
                }
                LifecycleEvent.Type.ERROR -> {
                    Log.i("ERROR", "!!")
                    Log.e("CONNECT ERROR", lifecycleEvent.exception.toString())
                }
            }
        }

        stompClient.topic("/sub/usermatch/${matchId}").subscribe {
            Log.i("tagg", it.toString())
            val newMessage = JSONObject(it.payload)
            val type = newMessage.getString("type")

            when (type) {
                "ENTER" -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        val response = DarlyService.getDarlyService().getMatchDetails(matchId)
                        model.matchUsers.value = response.body()?.users ?: listOf()

                        myUserId = response.body()?.myUserId ?: 3

                        binding.matchTitle.text = response.body()?.matchTitle ?: ""
                        binding.hostNickname.text = response.body()?.hostNickname
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
                        binding.matchUsersList.layoutManager =
                            GridLayoutManager(this@MatchLobbyActivity, 1)
                    }
                }
                "READY" -> Log.d("READY", "READY")
            }
        }
    }

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
//        runStomp()
        CoroutineScope(Dispatchers.Main).launch {
            val response = DarlyService.getDarlyService().getMatchDetails(matchId)
            model.matchUsers.value = response.body()?.users ?: listOf()

            myUserId = response.body()?.myUserId ?: 3

            binding.matchTitle.text = response.body()?.matchTitle ?: ""
            binding.hostNickname.text = response.body()?.hostNickname
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
            runStomp()
            val data = JSONObject()
            data.put("type", "ENTER")
            data.put("userNickname", "darly1")
            data.put("matchId", matchId)
            data.put("userId", myUserId)
            stompClient.send("/pub/usermatch", data.toString()).subscribe()

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

//    fun subscribeObserver() {
//        model.matchUsers.observe(this, Observer {
//            adapter.users = model.matchUsers.value as MutableList<MatchUsers>
//        })
//    }
}