package com.ssafy.darly.model

import com.ssafy.darly.model.record.RecordRequest
import com.ssafy.darly.model.socket.UserModel

data class CompetitorInfo(
    val record: RecordRequest,
    val userList: List<UserModel>
)