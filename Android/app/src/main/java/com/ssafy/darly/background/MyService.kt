package com.ssafy.darly.background

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.MutableLiveData
import com.ssafy.darly.R
import com.ssafy.darly.activity.RunningActivity
import com.ssafy.darly.activity.RunningActivity.Companion.ACTION_STOP
import com.ssafy.darly.util.LocationHelper
import kotlin.concurrent.thread

class MyService : Service() {
    val binder = MyBinder()
    var started = false
    val time = MutableLiveData<Int>()

    // 위경도, 이전 위경도
    var befLoc : Location? = null
    var totalDist = MutableLiveData<Float>()

    // 이동경로,시간,속력등 정보
    var locationList = MutableLiveData<ArrayList<Location>>()

    init {
        totalDist.value = 0.0f
        time.value = 0
        locationList.value = ArrayList()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) : Int {
        Log.d("MyService","서비스 시작!!")
        // 서비스 중지
        if (intent?.action != null && intent.action.equals(ACTION_STOP, ignoreCase = true)) {
            stopForeground(true)
            stopSelf()
            started = false
        }
        else{
            // Started Service에서 서비스 시작시 호출
            generateForegroundNotification()

            thread (start = true){
                if(!started){
                    Log.d("MyService","서비스 시작")
                    started = true
                    while(started){
                        Thread.sleep(1000)
                        time.postValue(time.value?.plus(1))
                    }
                }
            }

            LocationHelper().startListeningUserLocation(this, object : LocationHelper.MyLocationListener {
                override fun onLocationChanged(location: Location) {
                    if(started){
                        locationList.value?.add(location)
                        // 이전기록이 없다면
                        if(befLoc != null){
                            totalDist.value = totalDist.value?.plus(location.distanceTo(befLoc))
                            //totalDist.value = totalDist.value?.plus(location.distanceTo(befLoc) * 10)
                        }
                        befLoc = location
                    }
                }
            })
        }
        // 서비스가 죽으면 시스템을 어떻게할지
        return START_NOT_STICKY
    }

    inner class MyBinder : Binder() {
        // 액티비티와 서비스가 연결되면 이 메서드를 통해 서비스에 접근
        fun getService(): MyService = this@MyService
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    //Notififcation for ON-going
    private var iconNotification: Bitmap? = null
    private var notification: Notification? = null
    var mNotificationManager: NotificationManager? = null
    private val mNotificationId = 123

    private fun generateForegroundNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intentMainLanding = Intent(this, RunningActivity::class.java)
            val pendingIntent =
                PendingIntent.getActivity(this, 0, intentMainLanding, PendingIntent.FLAG_MUTABLE)
            iconNotification = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            if (mNotificationManager == null) {
                mNotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                assert(mNotificationManager != null)
                mNotificationManager?.createNotificationChannelGroup(
                    NotificationChannelGroup("chats_group", "Chats")
                )
                val notificationChannel =
                    NotificationChannel("service_channel", "Service Notifications",
                        NotificationManager.IMPORTANCE_MIN)
                notificationChannel.enableLights(false)
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_SECRET
                mNotificationManager?.createNotificationChannel(notificationChannel)
            }
            val builder = NotificationCompat.Builder(this, "service_channel")

            builder.setContentTitle(StringBuilder(resources.getString(R.string.app_name)).append(" 가 동작중이에요").toString())
                .setTicker(StringBuilder(resources.getString(R.string.app_name)).append("기록중입니다.").toString())
                .setContentText("앱 열기") //                    , swipe down for more options.
                .setSmallIcon(R.drawable.ic_logo)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setWhen(0)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
            if (iconNotification != null) {
                builder.setLargeIcon(Bitmap.createScaledBitmap(iconNotification!!, 128, 128, false))
            }
            notification = builder.build()
            //startForeground(mNotificationId, notification)
            NotificationManagerCompat.from(this).apply {
                notify(mNotificationId, notification!!)
            }
        }
    }
}