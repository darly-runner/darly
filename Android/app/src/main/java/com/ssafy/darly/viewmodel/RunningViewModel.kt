package com.ssafy.darly.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Math.round
import kotlin.math.roundToInt

class RunningViewModel : ViewModel(){
    var target = MutableLiveData<String>()

    var timeCnt = 0f

    var dist = MutableLiveData<Float>()        // 거리
    var speed = MutableLiveData<Float>()       // 속력
    var time = MutableLiveData<String>()        // 시간
    var pace = MutableLiveData<Float>()        // 페이스

    var kalory = MutableLiveData<String>()
    var heartRate = MutableLiveData<String>()

    val isPause = MutableLiveData<Boolean>()

    init {
        dist.value = 0.0f
        speed.value = 0.0f
        time.value = "00:00"
        pace.value = 0.0f

        isPause.value = false
    }

    fun setTime(t : Int){
        timeCnt = t.toFloat()

        var m = t / 60
        var second = (t % 60).toString()

        var hour = (m / 60).toString()
        var minute = (m % 60).toString()

        if(minute.length == 1)
            minute = "0$minute"

        if(second.length == 1)
            second = "0$second"

        if(hour != "0")
            time.postValue("$hour:$minute:$second")
        else
            time.postValue("$minute:$second")
    }

    fun setDist(d : Float){
        // 소수점 한자리만 남긴다.
        dist.value = (d / 1000f * 100f).roundToInt() / 100f
    }

    fun setSpeed(){
        var s = dist.value?.div(timeCnt / 3600f)
        if (s != null)
            speed.value = round((s * 10f)) / 10f
    }

    fun setPace(){
        pace.value = timeCnt * 60f / dist.value!!
    }
}