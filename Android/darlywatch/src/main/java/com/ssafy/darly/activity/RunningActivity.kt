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
import androidx.room.Room
import androidx.viewpager.widget.ViewPager
import androidx.wear.ambient.AmbientModeSupport
import com.ssafy.darly.BuildConfig
import com.ssafy.darly.R
import com.ssafy.darly.adapter.RunningViewPagerAdapter
import com.ssafy.darly.background.MyService
import com.ssafy.darly.dao.AppDatabase
import com.ssafy.darly.databinding.ActivityRunningBinding
import com.ssafy.darly.model.RecordRequest
import com.ssafy.darly.service.DarlyService
import com.ssafy.darly.util.GlobalApplication
import com.ssafy.darly.viewmodel.RunningViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RunningActivity : AppCompatActivity(), AmbientModeSupport.AmbientCallbackProvider{
    private lateinit var binding: ActivityRunningBinding
    private val model: RunningViewModel by viewModels()

    private lateinit var service : MyService
    private var bound: Boolean = false

    private val adapter by lazy { RunningViewPagerAdapter(supportFragmentManager) }
    private lateinit var ambientController: AmbientModeSupport.AmbientController

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRunningBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = model
        setContentView(binding.root)

        ambientController = AmbientModeSupport.attach(this)

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
            Log.d("RunningActivity", "???????????? ???????????? ???????????? ????????? ???????????? ???????????????.")
        }else{
            service.time.value = service.time.value?.plus(1)
            service.totalDist.value = service.totalDist.value?.plus(0.01f)
            Log.d("RunningActivity", "???????????? ???????????? ???????????? ????????? ?????????????????? ???????????????.")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection);
    }

    override fun onBackPressed() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("????????? ?????????????????????????")
        builder.setIcon(R.mipmap.ic_launcher)

        var record = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                CoroutineScope(Dispatchers.IO).launch {
                    // ???????????? ????????? ??????????????? ??????DB??? ????????????.
                    if(GlobalApplication.network.getNetworkConnected()) {
                        DarlyService.getDarlyService().postRecord(model.record())

                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP //???????????? ????????????

                        startActivity(intent)
                    }
                    // ???????????? ????????? ?????????????????? ??????DB??? ????????????.
                    else{
                        saveLocalDb(model.record())

                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP //???????????? ????????????

                        startActivity(intent)
                    }
                    serviceStop()
                }
            }
        }

        var cancle = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                model.isPause.value = true
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP //???????????? ????????????
                startActivity(intent)
            }
        }


        builder.setPositiveButton("?????? ??????", record)
        builder.setNeutralButton("?????? ??????", cancle)
        builder.setNegativeButton("????????????", null)
        builder.show()
    }

    fun subscribeObserver(){
        // ?????????
        service.time.observe(this, Observer { time ->
            model.setTime(time)
        })

        // ????????????
        service.totalDist.observe(this, Observer { dist->
            model.setDist(dist)
            model.setSpeed()
            model.setPace()
            model.setCalorie()
            model.setPaceBySection(1f)

            model.locationList.value = service.locationList.value
        })

        // ??????????????? ????????? ????????????????????? ????????????.
        model.isPause.observe(this, Observer { isPause ->
            if(isPause){
                serviceStop()
                Log.d("MainActivity", "???????????? ?????????.")
                Toast.makeText(this,"???????????? ?????????.",Toast.LENGTH_SHORT).show()
            }else{
                serviceStart()
                Log.d("MainActivity", "????????? ???????????????.")
                Toast.makeText(this,"????????? ???????????????.",Toast.LENGTH_SHORT).show()
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

    fun saveLocalDb(record : RecordRequest){
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "recordDB"
        ).build()

        // DB??? ?????????.
        db.recordDao().insertRecord(
            GlobalApplication.network.recordToDto(record)
        )
    }

    companion object{
        const val  ACTION_STOP = "${BuildConfig.APPLICATION_ID}.stop"
    }

    // ???????????? ????????? CallBack
    override fun getAmbientCallback(): AmbientModeSupport.AmbientCallback {
        return object : AmbientModeSupport.AmbientCallback() {
            override fun onEnterAmbient(ambientDetails: Bundle) {
                super.onEnterAmbient(ambientDetails)
                Log.d("onEnterAmbient","???????????? ??????")
            }

            override fun onExitAmbient() {
                super.onExitAmbient()
                Log.d("onExitAmbient","???????????? ???")
            }
        }
    }
}