package com.ssafy.darly.activity

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.darly.R
import com.ssafy.darly.adapter.crew.CrewMatchLobbyAdapter
import com.ssafy.darly.databinding.ActivityMatchLobbyBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.CrewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent

class MatchLobbyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchLobbyBinding
    private val model: CrewViewModel by viewModels()
    lateinit var adapter: CrewMatchLobbyAdapter
    private var matchId: Long = 0
    private var myUserId: Long = 0
    private var isHost: Int = 0
    private var prevStatus: String = "N"
    private var readyCount : Int = 0
    var currentNum : Int = 0

    private val url = "http://3.36.61.107:8000/ws/websocket"
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
            val newMessage = JSONObject(it.payload)
            val type = newMessage.getString("type")
            val userId = newMessage.getString("userId")
            val isReady = newMessage.getString("isReady")

            when (type) {
                "ENTER" -> {
                    if (userId != myUserId.toString()) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val response = DarlyService.getDarlyService().getMatchDetails(matchId)
                            model.matchUsers.value = response.body()?.users ?: listOf()

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
                }
                "READY" -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        val response = DarlyService.getDarlyService().refreshMatchDetails(matchId)
                        model.matchUsers.value = response.body()?.users ?: listOf()

                        adapter = CrewMatchLobbyAdapter(
                            model.matchUsers.value!!,
                            LayoutInflater.from(this@MatchLobbyActivity),
                            glide
                        )
//                        adapter.notifyDataSetChanged()
                        binding.matchUsersList.adapter = adapter
                        binding.matchUsersList.layoutManager =
                            GridLayoutManager(this@MatchLobbyActivity, 1)
                    }
                    if (isReady == "R") {
                        readyCount++
                    } else if (isReady == "N") {
                        readyCount--
                    }
                    Log.d("cur NUMBER", currentNum.toString())
                    Log.d("ready COUNT", readyCount.toString())
                    if((isHost == 1) && (readyCount == currentNum)) {
                        binding.readyButton.setBackgroundResource(R.drawable.button_background_lg)
                        binding.readyButton.setTextColor(Color.rgb(247, 248, 251))
                        binding.readyButton.setOnClickListener {
                            Log.d("!! START", "START!!!!!")
                            val data = JSONObject()
                            data.put("type", "START")
                            data.put("matchId", matchId)
                            stompClient.send("/pub/usermatch", data.toString()).subscribe()

                        }
                    }
                }
                "LEAVE" -> {
                    if (userId != myUserId.toString()) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val response = DarlyService.getDarlyService().refreshMatchDetails(matchId)
                            model.matchUsers.value = response.body()?.users ?: listOf()

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
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_match_lobby)
        binding.lifecycleOwner = this
        binding.viewModel = model
//        subscribeObserver()
        val glide = Glide.with(this)
        matchId = intent.getLongExtra("matchId", 0)
//        Log.


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
            isHost = response.body()?.imHost ?: 0

            if (isHost == 1) {
                binding.readyButton.text = "START"
                binding.readyButton.setBackgroundResource(R.drawable.button_background_stroke)
                binding.readyButton.setTextColor(Color.rgb(114, 87, 93))

//                val data = JSONObject()
//                data.put("type", "READY")
//                data.put("matchId", matchId)
//                data.put("userId", myUserId)
//                data.put("isReady", "R")
//                stompClient.send("/pub/usermatch", data.toString()).subscribe()


            } else if (isHost == 0) {
                binding.readyButton.setOnClickListener {
                    prevStatus = if (prevStatus == "N") {
                        "R"
                    } else {
                        "N"
                    }
                    val data = JSONObject()
                    data.put("type", "READY")
                    data.put("matchId", matchId)
                    data.put("userId", myUserId)
                    data.put("isReady", prevStatus)
                    stompClient.send("/pub/usermatch", data.toString()).subscribe()
                }
            }

            binding.matchTitle.text = response.body()?.matchTitle ?: ""
            binding.hostNickname.text = response.body()?.hostNickname
            binding.goalDistance.text = response.body()?.matchGoalDistance.toString()
            currentNum = response.body()?.matchCurPerson?.toInt() ?: 0
            binding.currentNum.text = response.body()?.matchCurPerson.toString()
//            binding.currentNum.text = model.matchUsers.value!!.size.toString()
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
            data.put("matchId", matchId)
            data.put("userId", myUserId)
            stompClient.send("/pub/usermatch", data.toString()).subscribe()
//            runStomp()
        }

//        binding.readyButton.setOnClickListener {
//            prevStatus = if (prevStatus == "N") {
//                "R"
//            } else {
//                "N"
//            }
//            val data = JSONObject()
//            data.put("type", "READY")
//            data.put("matchId", matchId)
//            data.put("userId", myUserId)
//            data.put("isReady", prevStatus)
//            stompClient.send("/pub/usermatch", data.toString()).subscribe()
//        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val data = JSONObject()
        data.put("type", "LEAVE")
        data.put("matchId", matchId)
        data.put("userId", myUserId)
        data.put("isHost", isHost)
        stompClient.send("/pub/usermatch", data.toString()).subscribe()
        finish()
    }

//    private fun subscribeObserver() {
//        model.matchUsers.observe(this) {
//            adapter.users = model.matchUsers.value as MutableList<MatchUsers>
//        }
//    }
}