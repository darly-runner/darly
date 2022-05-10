package com.ssafy.darly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.darly.model.address.Address

class MypageUpdateViewModel : ViewModel() {
    var userNickname = MutableLiveData<String>()
    var userMessage = MutableLiveData<String>()
    var userAddress = MutableLiveData<List<Address>>()
    var userImage = MutableLiveData<String>()

    init {
        userAddress.value = listOf()
    }
}