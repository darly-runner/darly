package com.ssafy.darly.model

data class SearchLocationReq(
    var address : String,
)

data class CreateMatchReq(
    var matchTitle: String,
    var matchMaxPerson: Short,
    var matchGoalDistance: Float
)