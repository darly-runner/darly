package com.ssafy.darly.model

data class UserGetRes(
    var message : String,
    var statusCode : Int,
    var userEmail : String,
    var userImage : String,
    var userMessage : String,
    var userNickname : String,
    var userPoint : Int,
    var userId: Long,
)