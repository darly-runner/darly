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

data class CrewRecommendationResponse(
    var message: String,
    var statusCode: Int,
    var size: Int,
    var totalCrew: Int,
    var currentPage: Int,
    var numberOfCrew: Int,
    var crews: List<CrewRecommendations>
)

data class CrewRecommendations(
    var crewId: Int,
    var crewName: String,
    var crewDesc: String,
    var crewImage: String,
    var crewAddress: String,
    var crewPeopleNum: Int,
)