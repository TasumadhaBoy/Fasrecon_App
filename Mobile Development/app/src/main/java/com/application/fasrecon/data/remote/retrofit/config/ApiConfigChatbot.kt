package com.application.fasrecon.data.remote.retrofit.config

import com.application.fasrecon.BuildConfig
import com.application.fasrecon.data.remote.retrofit.service.ApiService
import com.application.fasrecon.data.remote.retrofit.service.ApiServiceChatbot
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfigChatbot {
    companion object {
        fun getApiService(): ApiServiceChatbot {

            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

            val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL_CHATBOT)
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()

            return retrofit.create(ApiServiceChatbot::class.java)
        }
    }
}