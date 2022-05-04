package com.ssafy.darly.activity

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ssafy.darly.BuildConfig
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
            model.isPause.value = true
        }

        // 계속 하기
        binding.resumeButton.setOnClickListener {
            model.isPause.value = false
        }

        // 종료
        binding.endButton.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
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

        // 이동거리
        service.dist.observe(this, Observer { dist->
            model.setDist(dist)
            model.setSpeed()
            model.setPace()
            model.setKalory()

            model.locationList.value = service.locationList.value
            Toast.makeText(this,"${service.locationList.value?.size} , 좌표크기",Toast.LENGTH_LONG).show()
        })

        // 일시정지를 누르면 일시정지화면을 보여준다.
        model.isPause.observe(this, Observer { isPause ->
            if(isPause){
                serviceStop()
                Log.d("MainActivity", "일시정지 입니다.")
                Toast.makeText(this,"일시정지 합니다.",Toast.LENGTH_SHORT).show()

                binding.pauseFragment.visibility = View.VISIBLE
                binding.endButton.visibility = View.VISIBLE
                binding.resumeButton.visibility = View.VISIBLE

                binding.pauseButton.isEnabled = false
                binding.resumeButton.isEnabled = true
                binding.endButton.isEnabled = true
            }else{
                serviceStart()
                Log.d("MainActivity", "운동을 시작합니다.")
                Toast.makeText(this,"운동을 시작합니다.",Toast.LENGTH_SHORT).show()

                binding.pauseFragment.visibility = View.INVISIBLE
                binding.endButton.visibility = View.INVISIBLE
                binding.resumeButton.visibility = View.INVISIBLE

                binding.pauseButton.isEnabled = true
                binding.resumeButton.isEnabled = false
                binding.endButton.isEnabled = false
            }
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

    fun serviceStart() {
        val intent = Intent(this, MyService::class.java)
        startService(intent)
        Intent(this, MyService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    fun serviceStop() {
        val intentStop = Intent(this,MyService::class.java)
        intentStop.action = ACTION_STOP
        startService(intentStop)
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        try {
            val manager =
                getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(
                Int.MAX_VALUE
            )) {
                if (serviceClass.name == service.service.className) {
                    return true
                }
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }

    companion object{
        const val  ACTION_STOP = "${BuildConfig.APPLICATION_ID}.stop"
    }
}