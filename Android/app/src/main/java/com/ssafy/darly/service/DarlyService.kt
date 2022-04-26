package com.ssafy.darly.service

import android.util.Log
import com.ssafy.darly.util.GlobalApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DarlyService {
    fun getDarlyService():ApiService{
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(AppInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl("http://3.36.61.107:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    class AppInterceptor : Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val token = GlobalApplication.prefs.getString("token","noToken")
            if(token == "noToken"){
                var newRequest = request().newBuilder()
                    .build()
                proceed(newRequest)
            }else{
                var newRequest = request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                proceed(newRequest)
            }

        }
    }
}