package com.ssafy.darly.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.darly.model.record.SectionString

class SectionViewModel : ViewModel() {
    var sections = MutableLiveData<List<SectionString>>()
    val minSectionValue = MutableLiveData<Int>()
    val minSectionIndex = MutableLiveData<Int>()
    val gapSectionValue = MutableLiveData<Int>()

    init {
        sections.value = listOf()
    }
}