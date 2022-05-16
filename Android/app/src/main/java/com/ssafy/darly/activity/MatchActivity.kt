package com.ssafy.darly.activity

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ssafy.darly.BuildConfig
import com.ssafy.darly.R
import com.ssafy.darly.adapter.match.MatchViewPagerAdapter
import com.ssafy.darly.background.MyService
import com.ssafy.darly.databinding.ActivityMatchBinding
import com.ssafy.darly.model.CompetitorInfo
import com.ssafy.darly.model.record.RecordRequest

import com.ssafy.darly.viewmodel.RunningViewModel

class MatchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchBinding
    private val model : RunningViewModel by viewModels()

    private val adapter = MatchViewPagerAdapter()
    private lateinit var service : MyService
    private var bound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_match)
        binding.lifecycleOwner = this
        binding.viewModel = model

        serviceStart()
        init()
    }

    fun init(){
        binding.matchViewPager.adapter = adapter

        binding.endButton.setOnClickListener {
            serviceStop()
        }

        val list = mutableListOf<CompetitorInfo>()

        for(i in 0 until 3){
            list.add(CompetitorInfo(
                RecordRequest(null, 0f, 0, 0, 0, 0f, 0,
                    null, null, ArrayList(), ArrayList(), ArrayList()
                )
            ))
        }
        adapter.list = list
        adapter.notifyDataSetChanged()
    }

    fun subscribeObserver(){
        // 시간초
        service.time.observe(this, Observer { time ->
            model.setTime(time)
        })

        // 이동거리
        service.totalDist.observe(this, Observer { dist->
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

    companion object{
        const val ACTION_STOP = "${BuildConfig.APPLICATION_ID}.stop"
    }
}