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
    var crew: List<CrewRecommendations>
)

data class CrewRecommendations(
    var crewId: Int,
    var crewName: String,
    var crewDesc: String,
    var crewImage: String,
    var crewAddress: String,
    var crewPeopleNum: Int,
)

data class CreateCrew(
    var message : String,
    var statusCode : Int,
)

data class SearchAddress(
    var message: String,
    var statusCode: Int,
    var addresses: List<MyAddress>
)

data class MyAddress(
    var addressId: Int,
    var addressName: String
)

data class CrewDetail(
    var statusCode: Int,
    var message: String,
    var crewName: String,
    var crewDesc : String,
    var crewNotice: String,
    var crewHost: String,
    var crewPeople: Long,
    var crewFeedNum: Long,
    var crewLocation: String,
    var crewImage: String,
    var crewStatus: String,
)

data class CrewSummary(
    var statusCode: Int,
    var message: String,
    var crewPeopleNum: Int,
    var crewDistance: Float,
    var crewTime: Long,
    var crewPace: Float,
    var ranks: List<CrewSummaryRankings>
)

data class CrewSummaryRankings(
    var userNickname: String,
    var userImage: String,
    var userDistance: String,
    var userPace: Float
)