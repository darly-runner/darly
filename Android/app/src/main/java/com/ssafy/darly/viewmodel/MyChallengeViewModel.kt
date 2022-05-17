package com.ssafy.darly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.darly.model.record.MyChallenge

class MyChallengeViewModel : ViewModel() {
    var myChallenges = MutableLiveData<List<MyChallenge>>()

    init {
        myChallenges.value = listOf()
    }
}