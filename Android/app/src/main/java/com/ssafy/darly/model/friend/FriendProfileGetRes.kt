package com.ssafy.darly.model.friend

data class FriendProfileGetRes(
    var userNickname: String,
    var userMessage: String,
    var userEmail: String,
    var userPoint: Int,
    var userImage: String,
    var userAddresses: List<String>,
    var userTotalDistance: Float,
    var userFriendNum: Long,
)