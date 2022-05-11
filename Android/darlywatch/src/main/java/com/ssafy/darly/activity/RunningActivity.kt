package com.ssafy.darly.activity

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.ssafy.darly.BuildConfig
import com.ssafy.darly.R
import com.ssafy.darly.adapter.RunningViewPagerAdapter
import com.ssafy.darly.background.MyService
import com.ssafy.darly.databinding.ActivityRunningBinding
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.viewmodel.RunningViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RunningActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRunningBinding
    private val model: RunningViewModel by viewModels()

    private lateinit var service : MyService
    private var bound: Boolean = false

    private val adapter by lazy { RunningViewPagerAdapter(supportFragmentManager) }

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRunningBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = model
        setContentView(binding.root)

        binding.viewPager.adapter = RunningActivity@adapter
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                binding.indicator0IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_white))
                binding.indicator1IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_white))

                when(p0){
                    0 -> binding.indicator0IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_color))
                    1 -> binding.indicator1IvMain.setImageDrawable(getDrawable(R.drawable.shape_circle_color))
                }
            }
        })
        if(!isMyServiceRunning(MyService::class.java)){
            serviceStart()
            Log.d("RunningActivity", "실행중인 서비스가 없으므로 새로운 서비스를 시작합니다.")
        }else{
            service.time.value = service.time.value?.plus(1)
            service.totalDist.value = service.totalDist.value?.plus(0.01f)
            Log.d("RunningActivity", "실행중인 서비스가 있으므로 기존의 서비스값으로 계속합니다.")
        }
    }

    override fun onBackPressed() {
        var builder = AlertDialog.Builder(this)
        var dialog = builder.create()
        builder.setTitle("기록을 저장하시겠습니까?")
        builder.setIcon(R.mipmap.ic_launcher)

        var listener = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1 == DialogInterface.BUTTON_POSITIVE){
                    CoroutineScope(Dispatchers.Main).launch {
                        DarlyService.getDarlyService().postRecord(model.record())

                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }
                }else if(p1 == DialogInterface.BUTTON_NEGATIVE){
                    dialog.dismiss()
                }
            }
        }
        builder.setPositiveButton("기록 저장", listener)
        builder.setNegativeButton("취소", null)
        builder.show()
    }

    fun exit(){
        super.onBackPressed()
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

            model.locationList.value = service.locationList.value
            Toast.makeText(this,"${model.paceSection.value?.size} , 섹션크기", Toast.LENGTH_LONG).show()
        })

        // 일시정지를 누르면 일시정지화면을 보여준다.
        model.isPause.observe(this, Observer { isPause ->
            if(isPause){
                serviceStop()
                Log.d("MainActivity", "일시정지 입니다.")
                Toast.makeText(this,"일시정지 합니다.",Toast.LENGTH_SHORT).show()
            }else{
                serviceStart()
                Log.d("MainActivity", "운동을 시작합니다.")
                Toast.makeText(this,"운동을 시작합니다.",Toast.LENGTH_SHORT).show()
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
        val intentStop = Intent(this, MyService::class.java)
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