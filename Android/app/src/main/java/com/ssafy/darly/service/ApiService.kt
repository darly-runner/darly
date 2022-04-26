package com.ssafy.darly.service

<<<<<<< HEAD
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService{
    @POST("accounts/google")
    fun accountGoogle(
        @Body tokenId : String,
    ): Response<String>
=======
import com.ssafy.darly.model.GoogleAccountRequest
import com.ssafy.darly.model.GoogleAccountResponse
import com.ssafy.darly.model.UserGetResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService{
    @POST("accounts/google")
    suspend fun accountGoogle(
        @Body tokenId : GoogleAccountRequest,
    ): Response<GoogleAccountResponse>

    @GET("users")
    suspend fun getUsers(
        @Header("Authorization") token : String,
        @Query("userId") userId : Int,
    ): Response<UserGetResponse>
>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
}