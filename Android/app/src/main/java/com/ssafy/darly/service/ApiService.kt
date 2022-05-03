package com.ssafy.darly.service

import com.ssafy.darly.model.*
import com.ssafy.darly.model.user.UserFeedGetRes
import com.ssafy.darly.model.user.UserProfileGetRes
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

    @GET("crew/my")
    suspend fun myCrewList(): Response<MyCrewResponse>

    @GET("crew")
    suspend fun getCrewList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("address") address: Int,
        @Query("key") key: String,
    ): Response<CrewRecommendationResponse>

    @GET("users/profile")
    suspend fun getUserProfile(): Response<UserProfileGetRes>

    @GET("users/feed")
    suspend fun getUserFeedList(@Query("page") page: Int): Response<UserFeedGetRes>
}