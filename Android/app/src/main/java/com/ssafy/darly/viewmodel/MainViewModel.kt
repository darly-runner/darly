package com.ssafy.darly.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel(){
    var testStr = MutableLiveData<Int>()

    init {
        testStr.value = 0
    }

    fun clickTest(){
        testStr.value = testStr.value?.plus(1)
        Log.d("Click", "${testStr.value} 번 클릭됨")
    }
}