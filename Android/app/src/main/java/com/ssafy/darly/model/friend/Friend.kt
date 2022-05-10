package com.ssafy.darly.model.friend

data class Friend(
    val userId: Long,
    var userNickname: String,
    var userMessage: String,
    var userImage: String,
)
