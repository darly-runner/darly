package com.ssafy.darly.model

data class MyCrewResponse(
    var message : String,
    var statusCode : Int,
    var crew: List<MyCrewDetails>,
)

data class MyCrewDetails(
    var crewName : String,
    var crewImage : String,
)