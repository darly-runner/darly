package com.ssafy.darly.model.user

data class UserProfilePatchReq(
    var userNickname: String,
    var userImage: String,
    var userMessage: String,
    var userAddresses: List<Long>,
)