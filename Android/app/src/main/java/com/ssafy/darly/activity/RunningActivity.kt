package com.ssafy.darly.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ssafy.darly.R
import com.ssafy.darly.background.MyService
import com.ssafy.darly.databinding.ActivityRunningBinding
import com.ssafy.darly.fragment.PauseFragment
import com.ssafy.darly.viewmodel.RunningViewModel


class RunningActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRunningBinding
    private val model: RunningViewModel by viewModels()

    private lateinit var service : MyService
    private var bound: Boolean = false

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_running)
        binding.lifecycleOwner = this
        binding.viewModel = model

        serviceStart()

        supportFragmentManager.beginTransaction()
            .replace(R.id.pauseFragment, PauseFragment())
            .commit()
        binding.pauseFragment.visibility = View.INVISIBLE

        // 일시정지
        binding.pauseButton.setOnClickListener {
            serviceStop()
            model.isPause.value = true
        }

        // 계속 하기
        binding.resumeButton.setOnClickListener {
            model.isPause.value= false
        }

        // 종료
        binding.endButton.setOnClickListener {
            // 결과페이지로 넘어가야함
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceStop()
    }

    fun subscribeObserver(){
        // 시간초
        service.time.observe(this, Observer { time ->
            model.setTime(time)
        })

        model.isPause.observe(this, Observer { isPause ->
            if(isPause){
                Log.d("MainActivity", "일시정지 입니다.")
                binding.pauseFragment.visibility = View.VISIBLE
                binding.endButton.visibility = View.VISIBLE
                binding.resumeButton.visibility = View.VISIBLE

                binding.pauseButton.isEnabled = false
                binding.resumeButton.isEnabled = true
                binding.endButton.isEnabled = true
            }else{
                bindService(Intent(this, MyService::class.java), connection, Context.BIND_AUTO_CREATE)

                Log.d("MainActivity", "운동을 계속합니다.")
                binding.pauseFragment.visibility = View.INVISIBLE
                binding.endButton.visibility = View.INVISIBLE
                binding.resumeButton.visibility = View.INVISIBLE

                binding.pauseButton.isEnabled = true
                binding.resumeButton.isEnabled = false
                binding.endButton.isEnabled = false
            }
        })
    }

    fun serviceStart() {
        val intent = Intent(this, MyService::class.java)
        Intent(this, MyService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }

        startService(intent)
    }

    // 2022.04.28 서비스 스탑이 안됨! 나중에 해결
    fun serviceStop() {
        unbindService(connection)
        bound = false
    }
}