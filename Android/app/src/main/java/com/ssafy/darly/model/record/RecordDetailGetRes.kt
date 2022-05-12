package com.ssafy.darly.model.record

import com.ssafy.darly.model.Section

data class RecordDetailGetRes(
    val recordId: Int,
    val recordTitle: String,
    val recordDate: String,
    val recordDistance: Float,
    val recordTime: Int,
    val recordPace: Int,
    val recordHeart: Int,
    val recordSpeed: Float,
    val recordCalories: Int,
    val recordImage: String,
    val coordinateLatitudes: ArrayList<String>,
    val coordinateLongitudes: ArrayList<String>,
    val sections: ArrayList<Section>,
    val recordRank: Int,
    val ranks: ArrayList<Rank>
)