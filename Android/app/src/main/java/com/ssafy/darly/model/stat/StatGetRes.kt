package com.ssafy.darly.model.stat

data class StatGetRes(
    val totalDistance: Float,
    val totalNum: Int,
    val totalTime: Int,
    val paceAvg: Float,
    val heartAvg: Int,
    val distances: List<Float>,
    val startYear: Int,
)