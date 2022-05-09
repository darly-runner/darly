package com.ssafy.darly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatViewModel : ViewModel(){
    var totalDistance = MutableLiveData<String>()
    var totalNum = MutableLiveData<String>()
    var totalTime = MutableLiveData<String>()
    var paceAvg = MutableLiveData<String>()
    var heartAvg = MutableLiveData<String>()
    var distances = MutableLiveData<List<Float>>()
    var date = MutableLiveData<String>()

    init {
        date.value = "이번주"
        distances.value = listOf()
    }
}