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
    var selectedFeed = MutableLiveData<String>()

    init {
        userFeedList.value = listOf()
        selectedFeed.value = "https://darly-bucket.s3.ap-northeast-2.amazonaws.com/user/3dd2067c-2d35-45a1-a151-26cff402990b.png"
    }
}