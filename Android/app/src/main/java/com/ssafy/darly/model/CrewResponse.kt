package com.ssafy.darly.model
import java.io.Serializable

data class MyCrewResponse(
    var message : String,
    var statusCode : Int,
    var crew: List<MyCrewDetails>,
)

data class MyCrewDetails(
    var crewId: Long,
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
    var crewId: Long,
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

data class CrewFeeds(
    var statusCode: Int,
    var message: String,
    var size: Int,
    var totalFeeds: Long,
    var currentPage: Int,
    var numberOfFeed: Int,
    var feeds: ArrayList<FeedsList>
): Serializable

data class FeedsList(
    var hostNickname: String,
    var hostImage: String,
    var feedId: Long,
    var feedTitle: String,
    var feedContent: String,
    var feedDate: String,
    var feedImage: String,
    var commentNum: Int,
): Serializable

data class FeedsDetail(
    var statusCode: Int,
    var message: String,
    var feeds: List<Feeds>,
)

data class Feeds(
    var hostNickname: String,
    var hostImage: String,
    var feedTitle: String,
    var feedContent: String,
    var feedDate: String,
    var feedImage: String,
    var commentNum: Int
)

data class FeedsComments(
    var commentId: Long,
    var userNickname: String,
    var userImage: String,
    var commentContent: String,
    var commentDate: String,
)

data class RoomsList(
    var statusCode: Int,
    var message: String,
    var size: Int,
    var totalMatches: Long,
    var currentPage: Int,
    var numberOfMatch: Int,
    var matches: List<MatchDetails>
)

data class MatchDetails(
    var hostNickname: String,
    var hostImage: String,
    var matchId: Long,
    var matchTitle: String,
    var matchMaxPerson: Short,
    var matchCurPerson: Short,
    var matchGoalDistance: Float,
    var matchDate: String,
    var matchStartTime: String,
    var matchStatus: String,
)

data class CrewJoin(
    var statusCode: Int,
    var message: String
)

data class MatchLobbyDetails(
    var myUserId: Long,
    var crewId: Long,
    var imHost: Int,
    var statusCode: Int,
    var message: String,
    var matchTitle: String,
    var matchMaxPerson: Short,
    var matchCurPerson: Short,
    var matchGoalDistance: Float,
    var hostNickname: String,
    var users: List<MatchUsers>
)

data class MatchUsers(
    var userId: Long,
    var userImage: String,
    var userTotalDistance: Float,
    var userNickname: String,
    var userPaceAvg: Float,
    var userStatus: String,
    var isHost: Int
)

data class GetOutMatch(
    var statusCode: Int
)

data class CreateFeed(
    var statusCode: Int,
    var message: String
)

data class CreateMatch(
    var statusCode: Int,
    var message: String
)
