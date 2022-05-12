package com.ssafy.darly.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.darly.model.record.Rank
import com.ssafy.darly.model.record.RankString
import com.ssafy.darly.model.record.SectionString

class RecordDetailViewModel : ViewModel() {
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
}