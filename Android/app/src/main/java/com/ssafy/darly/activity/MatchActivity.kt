package com.ssafy.darly.activity

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ssafy.darly.BuildConfig
import com.ssafy.darly.R
import com.ssafy.darly.adapter.match.MatchViewPagerAdapter
import com.ssafy.darly.background.MyService
import com.ssafy.darly.databinding.ActivityMatchBinding
import com.ssafy.darly.model.socket.UserModel
import com.ssafy.darly.viewmodel.RunningViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import java.lang.reflect.Type


class MatchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchBinding
    private val model: RunningViewModel by viewModels()

    private val adapter = MatchViewPagerAdapter()
    private lateinit var service: MyService
    private var bound: Boolean = false

    private var matchId: Long = 0
    private var isHost: Int = 0
    private var myUserId: Long = 0
    private var crewId: Long = 0
    private var url = "http://3.36.61.107:8000/ws/websocket"
    val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

    private val userMap = mutableMapOf<Long, Int>()
    private var userList = mutableListOf<UserModel>()

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

            when (type) {
                "USER" -> {
                    if (userId == myUserId.toString()) {
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(this@MatchActivity, "${newMessage.getString("users")}", Toast.LENGTH_SHORT).show()
                            userList = parseJSON(newMessage.getString("users"))
                            val myUserList = mutableListOf<UserModel>()
                            myUserList.addAll(userList)
                            for ((index, user) in myUserList.withIndex()) {
                                if (user.userId == myUserId) {
                                    myUserList.removeAt(index)
                                    continue
                                }
                                user.userNowDistance = 0F
                                user.userNowPace = 0
                                user.nowRank = 1
                                user.nowTime = 0

                                user.distance = "0.0"
                                user.pace = "--"
                                user.rank = "${user.nowRank}등"
                                user.time = "00:00"
                            }
                            adapter.list = myUserList
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
                "PACE" -> {
                    Toast.makeText(this@MatchActivity, "들어옴", Toast.LENGTH_SHORT).show()
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(this@MatchActivity, "${newMessage.getString("nowPaces")}", Toast.LENGTH_SHORT).show()
                        Log.d("response", "${newMessage.getString("nowPaces")}")
                        var userList = parseJSON(newMessage.getString("nowPaces"))
                        for (user in userList) {
                            user.userNowDistance = 0F
                            user.userNowPace = 0
                            user.nowRank = 1
                            user.nowTime = 0

                            user.distance = "0.0"
                            user.pace = "--"
                            user.rank = "${user.nowRank}등"
                            user.time = "00:00"
                        }
                        adapter.list = userList
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_match)
        binding.lifecycleOwner = this
        binding.viewModel = model

        matchId = intent.getLongExtra("matchId", 0)
        myUserId = intent.getLongExtra("myUserId", 0)
        isHost = intent.getIntExtra("isHost", 0)
        crewId = intent.getLongExtra("crewId", 0)

        serviceStart()
        init()
    }

    fun init() {
        runStomp()
        val data = JSONObject()
        data.put("type", "USER")
        data.put("userId", myUserId)
        data.put("matchId", matchId)
        stompClient.send("/pub/usermatch", data.toString()).subscribe()
//        runStomp()

        binding.matchViewPager.adapter = adapter

        binding.endButton.setOnClickListener {
            serviceStop()
            leaveService()
        }

    }

    fun subscribeObserver() {
        // 시간초
        service.time.observe(this, Observer { time ->
            model.setTime(time)
        })

        // 이동거리
        service.totalDist.observe(this, Observer { dist ->
            model.setDist(dist)
            model.setSpeed()
            model.setPace()
            model.setCalorie()
            model.setPaceBySection(1f)

            binding.progressBar.progress = model.getRate()?.toInt() ?: 0
            model.locationList.value = service.locationList.value
            val data = JSONObject()
            data.put("type", "PACE")
            data.put("nowDistance", service.totalDist.value)
            data.put("nowTime", service.time.value)
            data.put("userId", myUserId)
            data.put("matchId", matchId)
//            val jsonObjectList = JSONArray()
//            for (user in userList) {
//                val tmpJSONObject = JSONObject()
//                tmpJSONObject.put("userId", user.userId)
//                tmpJSONObject.put("userNowDistance", user.userNowDistance)
//                tmpJSONObject.put("nowTime", user.nowTime)
//                tmpJSONObject.put("nowPace", 0)
//                jsonObjectList.put(tmpJSONObject)
//            }
//            data.put("Paces", jsonObjectList)
            stompClient.send("/pub/usermatch", data.toString()).subscribe()
        })
    }

    // Service와 Running Activity를 Binding
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, ibinder: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = ibinder as MyService.MyBinder
            service = binder.getService()
            bound = true

            subscribeObserver()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            bound = false
        }
    }

    private fun parseJSON(jsonString: String): MutableList<UserModel> {
        val type: Type = object : TypeToken<List<UserModel>>() {}.getType()
        return Gson().fromJson(jsonString, type)
    }

    private fun serviceStart() {
        val intent = Intent(this, MyService::class.java)
        startService(intent)
        Intent(this, MyService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun serviceStop() {
        val intentStop = Intent(this, MyService::class.java)
        intentStop.action = ACTION_STOP
        startService(intentStop)
    }

    companion object {
        const val ACTION_STOP = "${BuildConfig.APPLICATION_ID}.stop"
    }

    private fun leaveService() {
        val data = JSONObject()
        data.put("type", "LEAVE")
        data.put("matchId", matchId)
        data.put("userId", myUserId)
        data.put("isHost", isHost)
        stompClient.send("/pub/usermatch", data.toString()).subscribe()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        serviceStop()
        leaveService()
        val intent = Intent(this, CrewDetailActivity::class.java)
        intent.putExtra("crewId", crewId)
        ContextCompat.startActivity(this, intent, null)
    }
}