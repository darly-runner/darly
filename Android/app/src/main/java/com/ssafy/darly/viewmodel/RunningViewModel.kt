package com.ssafy.darly.viewmodel

import android.content.Context
import android.location.Location
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.ssafy.darly.model.record.RecordRequest
import com.ssafy.darly.model.Section
import java.lang.Math.round
import kotlin.coroutines.coroutineContext
import kotlin.math.roundToInt

class RunningViewModel : ViewModel(){
    var target = MutableLiveData<String>()

    var timeCnt = 0         // 시간 값
    var paceCnt = 0         // 페이스 값
    var calorieCnt = 0

    var dist = MutableLiveData<Float>()         // 거리
    var speed = MutableLiveData<Float>()        // 속력
    var time = MutableLiveData<String>()        // 시간
    var pace = MutableLiveData<String>()        // 페이스
    var calorie = MutableLiveData<String>()     // 칼로리
    var heartRate = MutableLiveData<String>()

    // 섹션별 거리,페이스,칼로리 기록
    var befTime = 0
    var paceSection = MutableLiveData<ArrayList<Section>>()

    var locationList = MutableLiveData<ArrayList<Location>>()

    val isPause = MutableLiveData<Boolean>()

    init {
        target.value = "5.0"
        dist.value = 0.0f
        speed.value = 0.0f
        time.value = "00:00"
        pace.value = "--"

        isPause.value = false

        paceSection.value = ArrayList()
    }

    fun getRate() : Float? {
        val targetCnt = target.value?.toFloat()
        var rate = targetCnt?.let { dist.value?.div(it) }
        rate = rate?.times(100)

        return rate
    }

    fun setTime(t : Int){
        timeCnt = t

        time.postValue(timeToStr(t))
    }

    // 거리
    fun setDist(d : Float){
        dist.value = (d / 1000f * 100f).roundToInt() / 100f
    }

    // 속력
    fun setSpeed(){
        var s = dist.value?.times(3600)?.div(timeCnt)
        speed.value = round((s?.times(10f)!!)) / 10f
    }

    // 페이스
    fun setPace(){
        if(dist.value != 0f){
            val p =  dist.value?.let { timeCnt.div(it) }
            val t = p?.toInt() ?: 0
            paceCnt = t
            pace.postValue(timeToStr(t))
        }
    }

    // n km 구간별 페이스
    fun setPaceBySection(d : Float){
        val size = paceSection.value?.size
        val distance = dist.value?.toInt()

        val currentTime = timeCnt - befTime
        val currentPace = currentTime
        val currentCalorie = (4 * currentTime * 75 / 3600)

        // 각 n km를 넘어설때마다 n-1에 저장
        if(d == 1f && size ?: 0 < distance ?: 0){
            // 이번 구간에서 소요된 시간 = 총시간 - 이전 n km 달성때의 시간
            paceSection.value?.add(Section(1f,currentPace,currentCalorie))
            befTime = timeCnt
        }else if(d == 0f){
            // 마지막 남은거리 처리
            if(dist.value != 0f){
                val sectionDist = dist.value?.minus(dist.value?.toInt()!!)
                val sectionPace = (currentTime / sectionDist!!).toInt()
                paceSection.value?.add(Section(sectionDist, sectionPace,currentCalorie))
            }
        }
    }

    fun setCalorie(){
        calorieCnt = (timeCnt / 10)
        calorie.value = "$calorieCnt kcal"
    }

    // time -> hh:mm:ss
    fun timeToStr(t : Int) : String{
        val m = t / 60
        var second = (t % 60).toString()

        val hour = (m / 60).toString()
        var minute = (m % 60).toString()

        if(minute.length == 1)
            minute = "0$minute"

        if(second.length == 1)
            second = "0$second"

        return if(hour != "0")
            "$hour:$minute:$second"
        else
            "$minute:$second"
    }

    fun record(): RecordRequest {
        val lat = ArrayList<String>()
        val lng = ArrayList<String>()

        for (i in locationList.value ?: ArrayList()) {
            lat.add(i.latitude.toString())
            lng.add(i.longitude.toString())
        }

        return RecordRequest(null, dist.value ?: 0f, paceCnt, calorieCnt,
            0, speed.value, timeCnt, null, null, lat, lng,
            paceSection.value ?: ArrayList()
        )
    }
}