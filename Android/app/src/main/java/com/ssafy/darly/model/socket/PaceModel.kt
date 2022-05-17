package com.ssafy.darly.model.socket

data class PaceModel(
    val userId: Long,
    val userNowDistance: Float,
    val nowTime: Int,
    val nowPace: Int,
)