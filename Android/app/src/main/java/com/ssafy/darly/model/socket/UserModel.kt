package com.ssafy.darly.model.socket

import java.io.Serializable

data class UserModel(
    val userId: Long,
    val userNickname: String,
    val userImage: String,

    var userNowDistance: Float,
    var userNowPace: Int,
    var nowTime: Int,
    var nowRank: Int,

    var distance: String,
    var pace: String,
    var time: String,
    var rank: String,
): Serializable