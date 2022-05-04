package com.ssafy.darly.model.user

data class UserProfileGetRes(
    var userNickname: String,
    var userMessage: String,
    var userEmail: String,
    var userPoint: Int,
    var userImage: String,
    var userAddress: List<String>,
    var userTotalDistance: Float,
    var userFriendNum: Long,
)