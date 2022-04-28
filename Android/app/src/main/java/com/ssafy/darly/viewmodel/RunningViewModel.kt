package com.ssafy.darly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RunningViewModel : ViewModel(){
    var target = MutableLiveData<String>()

    var distance = MutableLiveData<String>()
    var time = MutableLiveData<String>()
    var pace = MutableLiveData<String>()

    var kalory = MutableLiveData<String>()
    var heartRate = MutableLiveData<String>()

    val isPause = MutableLiveData<Boolean>()

    init {
        distance.value = "0"
        time.value = "0 : 00"
        pace.value = "0 : 00"

        isPause.value = false
    }

    fun setTime(t : Int){
        val minute = t / 60
        val second = t % 60

        time.postValue("$minute : $second")
    }
}