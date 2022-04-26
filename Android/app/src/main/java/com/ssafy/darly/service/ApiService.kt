<<<<<<< HEAD
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
=======
package com.ssafy.darly.service

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
>>>>>>> 6446e3b831eb911e114be9c28feb2f3450c1622b
}