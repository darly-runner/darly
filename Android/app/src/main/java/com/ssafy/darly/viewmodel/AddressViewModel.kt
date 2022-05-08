package com.ssafy.darly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.darly.model.address.Address

class AddressViewModel : ViewModel() {
    var addresses = MutableLiveData<List<Address>>()

    init {
        addresses.value = listOf()
    }
}