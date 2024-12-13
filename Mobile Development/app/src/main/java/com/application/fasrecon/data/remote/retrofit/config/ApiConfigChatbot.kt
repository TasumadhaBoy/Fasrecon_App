package com.application.fasrecon.data.remote.retrofit.config

import com.application.fasrecon.BuildConfig
import com.application.fasrecon.data.remote.retrofit.service.ApiServiceChatbot
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfigChatbot {
    companion object {
        fun getApiService(): ApiServiceChatbot {

            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()

            val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL_CHATBOT)
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()

            return retrofit.create(ApiServiceChatbot::class.java)
        }
    }
}