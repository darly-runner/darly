package com.ssafy.darly.service

import com.ssafy.darly.model.*
import com.ssafy.darly.model.friend.FriendListGetRes
import com.ssafy.darly.model.user.UserFeedGetRes
import com.ssafy.darly.model.user.UserProfileGetRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("accounts/google")
    suspend fun accountGoogle(
        @Body tokenId: AccountLoginReq,
    ): Response<AccountLoginRes>

    @POST("accounts/kakao")
    suspend fun accountKakao(
        @Body tokenId: AccountLoginReq,
    ): Response<AccountLoginRes>

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
    suspend fun getFriendProfile(@Path("friendId") friendId: Long): Response<UserProfileGetRes>

    @GET("friends/{friendId}/feed")
    suspend fun getFriendFeedList(@Path("friendId") friendId: Long, @Query("page") page: Int): Response<UserFeedGetRes>

    @POST("friends/{friendId}/accept")
    suspend fun acceptFriend(@Path("friendId") friendId: Long) :Response<BaseRes>

    @DELETE("friends/{friendId}/deny")
    suspend fun denyFriend(@Path("friendId") friendId: Long) :Response<BaseRes>
}