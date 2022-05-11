package com.ssafy.darly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.darly.model.record.Record

class RecordViewModel : ViewModel() {
    var records = MutableLiveData<List<Record>>()

    init {
        records.value = mutableListOf()
    }
}
