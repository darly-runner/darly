package com.ssafy.darly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.darly.model.friend.Friend

class FriendWaitingViewModel : ViewModel() {
    var friendWaitingList = MutableLiveData<List<Friend>>()

    init {
        friendWaitingList.value = listOf()
    }
}
