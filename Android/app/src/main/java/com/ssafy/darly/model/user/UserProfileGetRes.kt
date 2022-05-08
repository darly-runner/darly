package com.ssafy.darly.model.user

import com.ssafy.darly.model.address.Address

data class UserProfileGetRes(
    var userNickname: String,
    var userMessage: String,
    var userEmail: String,
    var userPoint: Int,
    var userImage: String,
    var userAddresses: List<Address>,
    var userTotalDistance: Float,
    var userFriendNum: Long,
)