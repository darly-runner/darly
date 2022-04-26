package com.ssafy.darly.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService{
    @POST("accounts/google")
    fun accountGoogle(
        @Body tokenId : String,
    ): Response<String>
}