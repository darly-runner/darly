
package com.ssafy.darly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.darly.model.*

class CrewViewModel : ViewModel(){
    var myCrewList = MutableLiveData<List<MyCrewDetails>>()
    var crewRecommendationList = MutableLiveData<List<CrewRecommendations>>()
    var myAddress = MutableLiveData<List<MyAddress>>()

    var crewId = MutableLiveData<Long>()
    var crewName = MutableLiveData<String>()
    var crewDesc = MutableLiveData<String>()
    var crewNotice = MutableLiveData<String>()
    var crewHost = MutableLiveData<String>()
    var crewPeopleNum = MutableLiveData<Long>()
    var crewLocation = MutableLiveData<String>()
    var crewImage = MutableLiveData<String>()

    var crewDetailRankings = MutableLiveData<List<CrewSummaryRankings>>()

    var crewDetailFeeds = MutableLiveData<List<FeedsList>>()

    var crewRoomsList = MutableLiveData<List<MatchDetails>>()

    var matchUsers = MutableLiveData<List<MatchUsers>>()
}