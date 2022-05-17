package com.ssafy.darly.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.ssafy.darly.model.record.Rank
import com.ssafy.darly.model.record.RankString
import com.ssafy.darly.model.record.SectionString

class RecordDetailViewModel : ViewModel() {
    val userImage = MutableLiveData<String>()
    val recordTitle = MutableLiveData<String>()
    val recordDate = MutableLiveData<String>()
    val recordDistance = MutableLiveData<String>()
    val recordTime = MutableLiveData<String>()
    val recordPace = MutableLiveData<String>()
    val recordHeart = MutableLiveData<String>()
    val recordSpeed = MutableLiveData<String>()
    val recordCalories = MutableLiveData<String>()
    val coordinateLatitudes = MutableLiveData<List<String>>()
    val coordinateLongitudes = MutableLiveData<List<String>>()
    val latLngList = MutableLiveData<List<LatLng>>()

    val userImageBitmap = MutableLiveData<Bitmap>()
    val isImageSet = MutableLiveData<Boolean>()

    val sections = MutableLiveData<List<SectionString>>()
    val recordRank = MutableLiveData<Int>()
    val recordRankString = MutableLiveData<String>()
    val ranks = MutableLiveData<List<Rank>>()

    val minSectionValue = MutableLiveData<Int>()
    val minSectionIndex = MutableLiveData<Int>()
    val gapSectionValue = MutableLiveData<Int>()

    val rankStringList = MutableLiveData<List<RankString>>()

    init {
        coordinateLatitudes.value = listOf()
        coordinateLongitudes.value = listOf()
        sections.value = listOf()
        ranks.value = listOf()
        rankStringList.value = listOf()
        recordRank.value = 0
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
}