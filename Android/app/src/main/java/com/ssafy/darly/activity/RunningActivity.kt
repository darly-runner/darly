package com.ssafy.darly.activity

import android.app.ActivityManager
import android.content.*
import android.location.Location
import android.os.*
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ssafy.darly.BuildConfig
import com.ssafy.darly.R
import com.ssafy.darly.background.MyService
import com.ssafy.darly.databinding.ActivityRunningBinding
import com.ssafy.darly.fragment.PauseFragment
import com.ssafy.darly.viewmodel.RunningViewModel
import java.util.*

class RunningActivity : AppCompatActivity() , TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityRunningBinding
    private val model: RunningViewModel by viewModels()

    private lateinit var service : MyService
    private var bound: Boolean = false


    private var tts: TextToSpeech? = null
    var cnt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_running)
        binding.lifecycleOwner = this
        binding.viewModel = model

        supportFragmentManager.beginTransaction()
            .replace(R.id.pauseFragment, PauseFragment())
            .commit()
        binding.pauseFragment.visibility = View.INVISIBLE
        model.target.value = intent.getStringExtra("target")

        tts = TextToSpeech(this, this)

        serviceStart()
        initBtn()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceStop()
//        if (tts != null) {
//            tts!!.stop()
//            tts!!.shutdown()
//        }
    }

    private fun initBtn(){
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
            model.setPaceBySection(0f)

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("record", model.record())
            startActivity(intent)
        }
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

            if(model.dist.value!! >= cnt){
                cnt++
                // 1. Vibrator 객체를 얻어온 다음
                val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
                // 2. 진동 구현: 600ms
                vibrator.vibrate(500)

                if(dist == 0f)
                    startTTS("기록을 시작합니다")
                else
                    startTTS("${dist} km 만큼 주행하였습니다.")
            }
        })

        // 일시정지를 누르면 일시정지화면을 보여준다.
        model.isPause.observe(this, Observer { isPause ->
            if(isPause){
                serviceStop()
                Toast.makeText(this,"일시정지 합니다.",Toast.LENGTH_SHORT).show()

                binding.pauseFragment.visibility = View.VISIBLE
                binding.endButton.visibility = View.VISIBLE
                binding.resumeButton.visibility = View.VISIBLE

                binding.pauseButton.isEnabled = false
                binding.resumeButton.isEnabled = true
                binding.endButton.isEnabled = true
            }else{
                serviceStart()
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

    private fun serviceStart() {
        val intent = Intent(this, MyService::class.java)
        startService(intent)
        Intent(this, MyService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun serviceStop() {
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

    // TTS 예제
    private fun startTTS(str : String) {
        tts!!.speak(str, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    // TextToSpeech override 함수
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