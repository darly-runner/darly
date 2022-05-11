package com.ssafy.darly.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.ssafy.darly.model.RecordRequest
import com.ssafy.darly.model.RecordRequestDto

class NetworkUtil(context: Context) {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    fun getNetworkConnected(): Boolean {
        val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
        val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true

        return isConnected
    }



    fun dtoToRecord(record : RecordRequestDto) : RecordRequest{
        return RecordRequest(
            null,
            record.recordDistance,
            record.recordPace,
            record.recordCalories,
            record.recordHeart,
            record.recordSpeed,
            record.recordTime,
            record.recordRank,
            record.recordTitle,
            record.coordinateLatitudes,
            record.coordinateLongitudes,
            record.sections,
        )
    }
    fun recordToDto(record : RecordRequest) : RecordRequestDto{
        return RecordRequestDto(
            null,
            null,
            record.recordDistance,
            record.recordPace,
            record.recordCalories,
            record.recordHeart,
            record.recordSpeed,
            record.recordTime,
            record.recordRank,
            record.recordTitle,
            record.coordinateLatitudes,
            record.coordinateLongitudes,
            record.sections,
        )
    }
}