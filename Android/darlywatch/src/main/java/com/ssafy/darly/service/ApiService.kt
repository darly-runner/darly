package com.ssafy.darly.service

import com.ssafy.darly.model.AccountLoginReq
import com.ssafy.darly.model.AccountLoginRes
import com.ssafy.darly.model.*
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

    @POST("records/watch")
    suspend fun postRecord(
        @Body recordReq : RecordRequest
    )
}