package com.ssafy.darly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.darly.model.user.Feed

class MypageViewModel : ViewModel(){
    var userNickname = MutableLiveData<String>()
    var userMessage =  MutableLiveData<String>()
    var userAddress = MutableLiveData<String>()
    var userTotalDistance = MutableLiveData<Float>()
    var userFriendNum = MutableLiveData<String>()
    var userImage = MutableLiveData<String>()
    var userFeedList = MutableLiveData<List<String>>()

    init {
        userFeedList.value = listOf()
    }
}