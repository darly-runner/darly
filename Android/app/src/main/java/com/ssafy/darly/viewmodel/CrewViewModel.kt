package com.ssafy.darly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.darly.model.CrewRecommendations
import com.ssafy.darly.model.MyCrewDetails

class CrewViewModel : ViewModel(){
    var myCrewList = MutableLiveData<List<MyCrewDetails>>()
    var crewRecommendationList = MutableLiveData<List<CrewRecommendations>>()
}