package com.ssafy.darly.activity

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.ssafy.darly.BuildConfig
import com.ssafy.darly.R
import com.ssafy.darly.adapter.match.MatchViewPagerAdapter
import com.ssafy.darly.background.MyService
import com.ssafy.darly.databinding.ActivityMatchBinding
import com.ssafy.darly.model.CompetitorInfo
import com.ssafy.darly.model.record.RecordRequest

import com.ssafy.darly.viewmodel.RunningViewModel
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent

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
            Log.d("USER WEBSOCKET", "${it}")
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
        data.put("matchId", matchId)
        stompClient.send("/pub/usermatch", data.toString()).subscribe()

        binding.matchViewPager.adapter = adapter

        binding.endButton.setOnClickListener {
            serviceStop()
            leaveService()
        }

        val list = mutableListOf<CompetitorInfo>()

        for (i in 0 until 3) {
            list.add(
                CompetitorInfo(
                    RecordRequest(
                        null, 0f, 0, 0, 0, 0f, 0,
                        null, null, ArrayList(), ArrayList(), ArrayList()
                    )
                )
            )
        }
        adapter.list = list
        adapter.notifyDataSetChanged()
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