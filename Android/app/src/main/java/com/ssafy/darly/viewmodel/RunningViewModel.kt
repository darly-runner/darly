package com.ssafy.darly.viewmodel

import android.location.Location
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
    var pace = MutableLiveData<String>()        // 페이스

    var kalory = MutableLiveData<String>()
    var heartRate = MutableLiveData<String>()

    var locationList = MutableLiveData<ArrayList<Location>>()

    val isPause = MutableLiveData<Boolean>()

    val test = MutableLiveData<String>()

    init {
        dist.value = 0.0f
        speed.value = 0.0f
        time.value = "00:00"
        pace.value = "0.0"

        isPause.value = false

        test.value = "test Text View"
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
        if(dist.value != 0f){
            var p =  dist.value?.let { timeCnt.div(it) }
            var t = p?.toInt() ?: 0

            var m = t / 60
            var second = (t % 60).toString()

            var hour = (m / 60).toString()
            var minute = (m % 60).toString()

            if(minute.length == 1)
                minute = "0$minute"

            if(second.length == 1)
                second = "0$second"

            if(hour != "0")
                pace.postValue("$hour:$minute:$second")
            else
                pace.postValue("$minute:$second")
        }
    }

    fun setKalory(){
        var k = (4 * (timeCnt / 3600) * 75).toInt()
        kalory.value = "$k kcal"
    }
}