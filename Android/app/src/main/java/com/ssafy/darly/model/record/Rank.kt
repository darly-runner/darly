package com.ssafy.darly.model.record

data class Rank(
    val userNickname: String,
    val userImage: String,
    val matchResultRank: Int,
    val matchResultTime: Int,
    val matchResultPace: Float
)