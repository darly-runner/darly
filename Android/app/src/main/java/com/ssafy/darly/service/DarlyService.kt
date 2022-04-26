package com.ssafy.darly.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DarlyService {
    fun getDarlyService():ApiService{
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
<<<<<<< HEAD
            .baseUrl("http://10.0.2.2:8000/")
=======
            .baseUrl("http://3.36.61.107:8000/")
>>>>>>> f5da09cd824a3d5676c830693e8c566ed06267e0
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
}