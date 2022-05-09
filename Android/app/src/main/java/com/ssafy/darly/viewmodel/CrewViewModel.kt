package com.ssafy.darly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.darly.model.CrewRecommendations
import com.ssafy.darly.model.MyAddress
import com.ssafy.darly.model.MyCrewDetails

class CrewViewModel : ViewModel(){
    var myCrewList = MutableLiveData<List<MyCrewDetails>>()
    var crewRecommendationList = MutableLiveData<List<CrewRecommendations>>()
    var myAddress = MutableLiveData<List<MyAddress>>()

    var crewName = MutableLiveData<String>()
    var crewDesc = MutableLiveData<String>()
    var crewNotice = MutableLiveData<String>()
    var crewHost = MutableLiveData<String>()
    var crewPeopleNum = MutableLiveData<Long>()
    var crewLocation = MutableLiveData<String>()
    var crewImage = MutableLiveData<String>()
}