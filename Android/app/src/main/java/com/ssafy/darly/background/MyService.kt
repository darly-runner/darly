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
import android.telephony.CarrierConfigManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.ssafy.darly.R
import com.ssafy.darly.activity.RunningActivity
import com.ssafy.darly.activity.RunningActivity.Companion.ACTION_STOP
import com.ssafy.darly.util.DistanceHelper
import com.ssafy.darly.util.LocationHelper
import java.text.SimpleDateFormat
import kotlin.concurrent.thread

class MyService : Service() {
    val binder = MyBinder()
    var started = false
    val time = MutableLiveData<Int>()

    // 위경도, 이전 위경도
    var bef_loc : Location? = null
    var dist = MutableLiveData<Float>()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) : Int {
        // 서비스 중지
        if (intent?.action != null && intent.action.equals(ACTION_STOP, ignoreCase = true)) {
            stopForeground(true)
            stopSelf()
            started = false
        }
        else{
            // Started Service에서 서비스 시작시 호출
            generateForegroundNotification()
            time.value = 0
            dist.value = 0f

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
                    Log.d("Location","" + location.latitude + "," + location.longitude)

                    // 이전기록이 없다면
                    if(bef_loc != null){
                        dist.value = dist.value?.plus(location.distanceTo(bef_loc))
                    }
                    bef_loc = location
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

            builder.setContentTitle(StringBuilder(resources.getString(R.string.app_name)).append(" service is running").toString())
                .setTicker(StringBuilder(resources.getString(R.string.app_name)).append("service is running").toString())
                .setContentText("Touch to open") //                    , swipe down for more options.
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
            startForeground(mNotificationId, notification)
        }
    }
}