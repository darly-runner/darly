package com.ssafy.darly.activity

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import android.util.Log
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
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import java.lang.reflect.Type
import java.util.*


class MatchActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
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
    private val targetDistance = 80f
    val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

    private var userList = mutableListOf<UserModel>()

    private var tts: TextToSpeech? = null
    var cnt = 0

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
                            userList = parseJSONUserList(newMessage.getString("users"))
                            for ((index, user) in userList.withIndex()) {
                                user.nowDistance = 0F
                                user.nowPace = "--"
                                user.nowRank = 1
                                user.nowTime = 0

                                user.distance = "0.0"
                                user.pace = "--"
                                user.rank = "${user.nowRank}???"
                                user.time = "00:00"

                                // ?????? ?????? ??????
                                if (user.userId == myUserId)
                                    model.rank.value = user.nowRank
                            }
                            adapter.list = userList
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
                "PACE" -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        userList = parseJSONUserList(newMessage.getString("nowPaces"))
                        for ((index, user) in userList.withIndex()) {
                            user.nowRank = index + 1
                            user.distance = String.format("%.02f", user.nowDistance)
                            user.rank = "${user.nowRank}???"
                            user.pace = String.format("%02d:%02d", user.nowPaceInt / 60, user.nowPaceInt % 60)
                            user.time = String.format("%02d:%02d:%02d", user.nowTime / 3600, user.nowTime / 60, user.nowTime % 60)

                            if (user.userId == myUserId)
                                model.rank.value = user.nowRank
                        }
                        adapter.list = userList
                        adapter.notifyDataSetChanged()
                    }
                }
                "END" -> {
                    serviceStop()
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

        tts = TextToSpeech(this, this)

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

            model.matchId = matchId
            model.setPaceBySection(0f)

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("record", model.record())
            startActivity(intent)
        }
    }

    fun subscribeObserver() {
        // ?????????
        service.time.observe(this, Observer { time ->
            model.setTime(time)
        })

        // ????????????
        service.totalDist.observe(this, Observer { dist ->
//            Toast.makeText(this, "${dist}", Toast.LENGTH_SHORT).show()
//            Log.d("end", "${dist}")

            model.setDist(dist)
            model.setSpeed()
            model.setPace()
            model.setCalorie()
            model.setPaceBySection(1f)

            model.locationList.value = service.locationList.value

            if (model.dist.value!! >= cnt) {
                cnt++
                // 1. Vibrator ????????? ????????? ??????
                val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
                // 2. ?????? ??????: 500ms
                vibrator.vibrate(500)

                if (dist == 0f)
                    startTTS("????????? ???????????????")
                else
                    startTTS("${dist} km ?????? ?????????????????????. ?????? ${model.rank.value}??? ?????????.")
            }
            binding.progressBar.progress = model.getRate()?.toInt() ?: 0
            model.locationList.value = service.locationList.value
            val data = JSONObject()
            data.put("type", "PACE")
            data.put("nowDistance", model.dist.value ?: 0)
            data.put("nowTime", service.time.value)
            data.put("nowPace", model.pace.value)
            data.put("nowPaceInt", model.paceCnt)
            data.put("userId", myUserId)
            data.put("matchId", matchId)
            stompClient.send("/pub/usermatch", data.toString()).subscribe()

            if (dist >= targetDistance) {
                val data = JSONObject()
                data.put("type", "END")
                data.put("nowTime", service.time.value)
                data.put("nowPaceInt", model.paceCnt)
                data.put("userId", myUserId)
                data.put("matchId", matchId)
                stompClient.send("/pub/usermatch", data.toString()).subscribe()
                serviceStop()
            }
        })
    }

    // Service??? Running Activity??? Binding
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

    private fun parseJSONUserList(jsonString: String): MutableList<UserModel> {
        val type: Type = object : TypeToken<List<UserModel>>() {}.getType()
        return Gson().fromJson(jsonString, type)
    }

    private fun parseJSONUserPace(jsonString: String): MutableList<UserModel> {
        val type: Type = object : TypeToken<UserModel>() {}.getType()
        return Gson().fromJson(jsonString, type)
    }

    companion object {
        const val ACTION_STOP = "${BuildConfig.APPLICATION_ID}.stop"
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

    private fun leaveService() {
        val data = JSONObject()
        data.put("type", "LEAVE")
        data.put("matchId", matchId)
        data.put("userId", myUserId)
        data.put("isHost", isHost)
        stompClient.send("/pub/usermatch", data.toString()).subscribe()
    }

    private fun startTTS(str: String) {
        tts!!.speak(str, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceStop()
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        leaveService()
        val intent = Intent(this, CrewDetailActivity::class.java)
        intent.putExtra("crewId", crewId)
        ContextCompat.startActivity(this, intent, null)
        serviceStop()
    }

    // TextToSpeech override ??????
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.KOREA)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                //doSomething
            } else {
                //doSomething
            }
        } else {
            //doSomething
        }
    }
}