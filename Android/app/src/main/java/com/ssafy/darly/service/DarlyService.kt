<<<<<<< HEAD
package com.ssafy.darly.service

import com.ssafy.darly.util.GlobalApplication
import com.ssafy.darly.util.PreferenceUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Header
import java.util.concurrent.TimeUnit

object DarlyService {
    fun getDarlyService():ApiService{
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
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
            val newRequest = request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            proceed(newRequest)
        }

    }
=======
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
            .baseUrl("http://3.36.61.107:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
>>>>>>> 6446e3b831eb911e114be9c28feb2f3450c1622b
}