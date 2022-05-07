package com.ssafy.darly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.darly.model.friend.Friend

class FriendAddViewModel : ViewModel() {
    var friendList = MutableLiveData<List<Friend>>()

    init {
        friendList.value = listOf()
    }
}