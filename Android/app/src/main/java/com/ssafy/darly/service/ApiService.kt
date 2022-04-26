package com.ssafy.darly.service

import com.ssafy.darly.model.GoogleAccountRequest
import com.ssafy.darly.model.GoogleAccountResponse
import com.ssafy.darly.model.UserGetResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService{
    @POST("accounts/google")
    suspend fun accountGoogle(
        @Body tokenId : GoogleAccountRequest,
    ): Response<GoogleAccountResponse>

    @GET("users")
    suspend fun getUsers(

        @Query("userId") userId : Int,
    ): Response<UserGetResponse>
}