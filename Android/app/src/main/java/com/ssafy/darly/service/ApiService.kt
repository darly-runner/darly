package com.ssafy.darly.service

import com.ssafy.darly.model.*
import com.ssafy.darly.model.address.AddressSearchGetRes
import com.ssafy.darly.model.friend.FriendApplyReq
import com.ssafy.darly.model.friend.FriendListGetRes
import com.ssafy.darly.model.friend.FriendProfileGetRes
import com.ssafy.darly.model.friend.FriendSearchReq
import com.ssafy.darly.model.record.RecordDetailGetRes
import com.ssafy.darly.model.record.RecordListGetRes
import com.ssafy.darly.model.record.RecordTitlePatchReq
import com.ssafy.darly.model.stat.StatGetRes
import com.ssafy.darly.model.user.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Account
    @POST("accounts/google")
    suspend fun accountGoogle(
        @Body tokenId: AccountLoginReq,
    ): Response<AccountLoginRes>

    @POST("accounts/kakao")
    suspend fun accountKakao(
        @Body tokenId: AccountLoginReq,
    ): Response<AccountLoginRes>

    // Record
    @Multipart
    @JvmSuppressWildcards
    @POST("records")
    suspend fun postRecord(
        @PartMap data: HashMap<String, RequestBody>,
        @Part recordImage: MultipartBody.Part?,
        @Part("coordinateLatitudes[]") coordinateLatitudes: List<RequestBody>,
        @Part("coordinateLongitudes[]") coordinateLongitudes: List<RequestBody>,
        @Part sections: List<MultipartBody.Part>
    ): Response<BaseRes>

    @GET("users")
    suspend fun getUsers(
        @Query("userId") userId: Int,
    ): Response<UserGetRes>

    // CREW
    @GET("crew/my")
    suspend fun myCrewList(): Response<MyCrewResponse>

    @GET("crew")
    suspend fun getCrewList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("address") address: Int,
        @Query("key") key: String,
    ): Response<CrewRecommendationResponse>

    @Multipart
    @POST("crew")
    suspend fun createCrew(
        @PartMap data: HashMap<String, RequestBody>,
        @Part crewImage: MultipartBody.Part?,
    ): Response<CreateCrew>

    @GET("addresses")
    suspend fun searchAddress(
        @Query("address") address: String
    ): Response<SearchAddress>

    @GET("crew/{crewId}")
    suspend fun getCrewDetail(
        @Path("crewId") crewId: Long
    ): Response<CrewDetail>

    @GET("crew/{crewId}/summary")
    suspend fun getCrewSummary(
        @Path("crewId") crewId: Long,
        @Query("type") type: String
    ): Response<CrewSummary>

    @GET("crew/{crewId}/feed")
    suspend fun getCrewFeeds(
        @Path("crewId") crewId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<CrewFeeds>

    @GET("feeds/{feedId}")
    suspend fun getFeedsDetail(
        @Path("feedId") feedId: Long,
    ): Response<FeedsDetail>

    @GET("crew/{crewId}/match")
    suspend fun getRoomsList(
        @Path("crewId") crewId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<RoomsList>

    @POST("crew/{crewId}/join")
    suspend fun crewJoin(
        @Path("crewId") crewId: Long,
    ): Response<CrewJoin>

    @GET("matches/{matchId}/in")
    suspend fun getMatchDetails(
        @Path("matchId") matchId: Long,
    ): Response<MatchLobbyDetails>

    @DELETE("matches/{matchId}/out")
    suspend fun getOutMatch(
        @Path("matchId") matchId: Long,
    ): Response<GetOutMatch>

    @POST("crew/{crewId}/match")
    suspend fun createMatch(
        @Path("crewId") crewId: Long,
        @Body createMatchReq: CreateMatchReq,
    ): Response<CreateMatch>

    @GET("matches/{matchId}/refresh")
    suspend fun refreshMatchDetails(
        @Path("matchId") matchId: Long,
    ): Response<MatchLobbyDetails>

    @Multipart
    @POST("crew/{crewId}/feed")
    suspend fun createFeed(
        @Path("crewId") crewId: Long,
        @PartMap data: HashMap<String, RequestBody>,
        @Part feedImage: MultipartBody.Part?,
    ): Response<CreateFeed>


    @GET("users/profile")
    suspend fun getUserProfile(): Response<UserProfileGetRes>

    @GET("users/feed")
    suspend fun getUserFeedList(@Query("page") page: Int): Response<UserFeedGetRes>

    @GET("friends")
    suspend fun getFriendList(): Response<FriendListGetRes>

    @GET("friends/waiting")
    suspend fun getFriendWaitingList(): Response<FriendListGetRes>

    @DELETE("friends/{friendId}")
    suspend fun deleteFriend(@Path("friendId") friendId: Long): Response<BaseRes>

    @GET("friends/{friendId}/profile")
    suspend fun getFriendProfile(@Path("friendId") friendId: Long): Response<FriendProfileGetRes>

    @GET("friends/{friendId}/feed")
    suspend fun getFriendFeedList(
        @Path("friendId") friendId: Long,
        @Query("page") page: Int
    ): Response<UserFeedGetRes>

    @POST("friends/{friendId}/accept")
    suspend fun acceptFriend(@Path("friendId") friendId: Long): Response<BaseRes>

    @DELETE("friends/{friendId}/deny")
    suspend fun denyFriend(@Path("friendId") friendId: Long): Response<BaseRes>

    @POST("friends/search")
    suspend fun getSearchFriend(@Body friendSearchReq: FriendSearchReq): Response<FriendListGetRes>

    @POST("friends")
    suspend fun applyFriend(@Body friendApplyReq: FriendApplyReq): Response<BaseRes>

    @Multipart
    @JvmSuppressWildcards
    @PATCH("users")
    suspend fun updateUserProfile(
        @PartMap data: HashMap<String, RequestBody>,
        @Part userImage: MultipartBody.Part?,
        @Part("userAddresses[]") userAddresses: List<RequestBody>
    ): Response<BaseRes>

    @Multipart
    @JvmSuppressWildcards
    @PATCH("users")
    suspend fun updateUserProfileWithoutImage(
        @PartMap data: HashMap<String, RequestBody>,
        @Part("userAddresses[]") userAddresses: List<RequestBody>
    ): Response<BaseRes>

    @POST("users/nickname")
    suspend fun checkNickname(@Body nicknameCheckPostReq: NicknameCheckPostReq): Response<NicknameCheckPostRes>

    //지영
    @GET("addresses")
    suspend fun searchAddresses(
        @Query("address") address: String
    ): Response<AddressSearchGetRes>

    @GET("stats/week")
    suspend fun getWeekStat(
        @Query("date") date: String
    ): Response<StatGetRes>

    @GET("stats/month")
    suspend fun getMonthStat(
        @Query("date") date: String
    ): Response<StatGetRes>

    @GET("stats/year")
    suspend fun getYearStat(
        @Query("date") date: String
    ): Response<StatGetRes>

    @GET("stats")
    suspend fun getAllStat(): Response<StatGetRes>

    @GET("records")
    suspend fun getRecordList(
        @Query("type") type: String
    ): Response<RecordListGetRes>

    @GET("records/{recordId}")
    suspend fun getRecordDetail(
        @Path("recordId") recordId: Long
    ): Response<RecordDetailGetRes>

    @PATCH("records/{recordId}")
    suspend fun updateRecordTitle(
        @Path("recordId") recordId: Long,
        @Body recordTitlePatchReq: RecordTitlePatchReq
    ): Response<BaseRes>
}