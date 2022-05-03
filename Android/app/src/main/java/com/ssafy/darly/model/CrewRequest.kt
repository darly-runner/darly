package com.ssafy.darly.model

data class CreateCrewReq(
    var crewName: String,
    var crewDesc: String,
    var crewAddress: Long,
//    var crewImage:
    var crewJoin: String
)