package com.ssafy.darly.service

import com.ssafy.darly.model.AccountLoginReq
import com.ssafy.darly.model.AccountLoginRes
import com.ssafy.darly.model.MyCrewResponse
import com.ssafy.darly.model.UserGetRes
import retrofit2.Response
import retrofit2.http.*

interface ApiService{
    @POST("accounts/google")
    suspend fun accountGoogle(
        @Body tokenId : AccountLoginReq,
    ): Response<AccountLoginRes>

    @POST("accounts/kakao")
    suspend fun accountKakao(
        @Body tokenId : AccountLoginReq,
    ): Response<AccountLoginRes>

    @GET("users")
    suspend fun getUsers(
        @Query("userId") userId : Int,
    ): Response<UserGetRes>
  
    @GET("crew/my")
      suspend fun myCrewList(): Response<MyCrewResponse>
}