package com.ssafy.darly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.darly.model.friend.Friend

class FriendViewModel : ViewModel() {
    var friendList = MutableLiveData<List<Friend>>()
    var friendWaitingList = MutableLiveData<List<Friend>>()
    var friendApplyMessage = MutableLiveData<String>()

    init {
        friendList.value = listOf()
        friendWaitingList.value = listOf()
    }
}
