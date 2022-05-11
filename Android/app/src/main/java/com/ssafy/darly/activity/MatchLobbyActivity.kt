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
    var myUserId: Long=0

//    lateinit var stompConnection: Disposable
//    lateinit var topic: Disposable

    val url = "http://3.36.61.107:8000/ws/websocket"
//    val intervalMills = 1000L
//    val client = OkHttpClient()
//    lateinit var stomp: StompClient

//    val stomp = StompClient()
//    val test = StompCommand()

    val stompClient =  Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

    fun runStomp() {
//        stompClient.
//        stompClient.topic("/chat/enter").subscribe { topicMessage ->
//            Log.i("message Recieve", topicMessage.payload)
//        }

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
        val data = JSONObject()
        data.put("userNickname", "darly1")
        data.put("matchId", matchId)
        data.put("userId", myUserId)
        stompClient.send("/usermatch", data.toString()).subscribe()


//        stompClient.lifecycle().subscribe { lifecycleEvent ->
//            when (lifecycleEvent.type) {
//                LifecycleEvent.Type.OPENED -> {
//                    Log.i("ENTER", "!!")
//                }
//                LifecycleEvent.Type.ERROR -> {
//                    Log.i("ERROR", "!!")
//                    Log.e("CONNECT ERROR", lifecycleEvent.exception.toString())
//                }
//                else ->{
//                    Log.i("ELSE", lifecycleEvent.message)
//                }
//            }
//        }
//        val data = JSONObject()
//        data.put("positionType", "1")
//        data.put("content", "test")
//        data.put("messageType", "CHAT")
//        data.put("destRoomCode", "test0912")
//
//        stompClient.send("/stream/chat/send", data.toString()).subscribe()
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
            Log.d("ppopoppopo", model.matchUsers.value.toString())

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