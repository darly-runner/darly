package com.ssafy.darly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.darly.model.record.Achieve

class AchieveViewModel : ViewModel() {
    var achieves = MutableLiveData<List<Achieve>>()
    var shortAchieves = MutableLiveData<List<Achieve>>()

    init {
        achieves.value = mutableListOf()
        shortAchieves.value = mutableListOf()
    }
}