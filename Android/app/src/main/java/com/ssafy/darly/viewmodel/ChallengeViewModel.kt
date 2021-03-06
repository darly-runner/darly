package com.ssafy.darly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.darly.model.ChallengeDetail

class ChallengeViewModel : ViewModel(){
    var challengesList = MutableLiveData<List<ChallengeDetail>>()
}