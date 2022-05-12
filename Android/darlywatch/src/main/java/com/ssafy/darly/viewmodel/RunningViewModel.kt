package com.ssafy.darly.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.darly.model.RecordRequest
import com.ssafy.darly.model.Section
import java.lang.Math.round
import kotlin.math.roundToInt

class RunningViewModel : ViewModel(){
    var target = MutableLiveData<String>()

    var timeCnt = 0         // 시간 값
    var paceCnt = 0         // 페이스 값
    var calorieCnt = 0
    var speedCnt = 0f

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
        dist.value = 0.0f
        speed.value = 0.0f
        time.value = "00:00"
        pace.value = "0.0"

        isPause.value = false

        paceSection.value = ArrayList()
    }

    fun setTime(t : Int){
        timeCnt = t

        time.postValue(timeToStr(t))
    }

    fun setDist(d : Float){
        // 소수점 한자리만 남긴다.
        dist.value = (d / 1000f * 100f).roundToInt() / 100f
    }

    fun setSpeed(){
        val s = dist.value?.div(timeCnt)?.times(3600)
        if (s != null)
            speed.value = round((s * 10f)) / 10f
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
        val currentPace = (currentTime)
        val currentCalorie = (4 * (currentTime/3600) * 75)

        // 각 n km를 넘어설때마다 n-1에 저장
        if(d == 1f && size?:0 < distance ?:0){
            // 이번 구간에서 소요된 시간 = 총시간 - 이전 n km 달성때의 시간
            paceSection.value?.add(Section(1f,currentPace,currentCalorie))
            befTime = timeCnt
        }else if(d == 0f){
            // 마지막 남은거리 처리
            // 0인것도 넘어오는데 테스트를위해 그냥 둿음
            paceSection.value?.add(Section(dist.value?.minus(dist.value?.toInt()!!) ?: 0.1f, currentPace,currentCalorie))
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

        return RecordRequest(
            null,
            dist.value ?: 0f,
            paceCnt,
            calorieCnt,
            0,
            speedCnt,
            timeCnt,
            null,
            null,
            lat,
            lng,
            paceSection.value ?: ArrayList()
        )
    }
}
