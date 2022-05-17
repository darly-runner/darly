package com.ssafy.darly.model.socket

import java.io.Serializable

data class UserModel(
    val userId: Long,
    val userNickname: String,
    val userImage: String,

    val userNowDistance: Float,
    val userNowPace: Integer,
    val nowTime: Int,
    val nowRank: Int,

    val distance: String,
    val pace: String,
    val time: String,
    val rank: String,
): Serializable