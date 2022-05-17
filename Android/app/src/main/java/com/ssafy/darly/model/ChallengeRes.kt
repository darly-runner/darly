package com.ssafy.darly.model

data class ChallengesList(
    var message: String,
    var statusCode: Int,
    var eventList: List<ChallengeDetail>
)

data class ChallengeDetail(
    var eventId: Int,
    var eventTitle: String,
    var UserNickname: String,
    var eventImage: String,
    var eventDate: Long,
)