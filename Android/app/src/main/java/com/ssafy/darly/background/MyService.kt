package com.ssafy.darly.background

import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.ssafy.darly.util.LocationHelper
import kotlin.concurrent.thread

class MyService : Service() {
    val binder = MyBinder()
    var started = false
    val time = MutableLiveData<Int>()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) : Int {
        // Started Service에서 서비스 시작시 호출
        time.value = 0

        thread (start = true){
            if(!started){
                started = true
                while(started){
                    Thread.sleep(1000)
                    time.postValue(time.value?.plus(1))
                    Log.d("Service : ", "${time.value}")
                }

                LocationHelper().startListeningUserLocation(this, object : LocationHelper.MyLocationListener {
                    override fun onLocationChanged(location: Location) {
                        Log.d("Location","" + location.latitude + "," + location.longitude)
                    }
                })
            }
        }

        // 서비스가 죽으면 지속적으로 시스템이 살려준다.
        return START_STICKY
    }

    inner class MyBinder : Binder() {
        // 액티비티와 서비스가 연결되면 이 메서드를 통해 서비스에 접근
        fun getService(): MyService = this@MyService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}