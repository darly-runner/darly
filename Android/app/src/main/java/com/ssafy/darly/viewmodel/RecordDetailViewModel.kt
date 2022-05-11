package com.ssafy.darly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
    val recordRank = MutableLiveData<String>()

    init {
        coordinateLatitudes.value = listOf()
        coordinateLongitudes.value = listOf()
    }
}