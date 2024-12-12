package com.application.fasrecon.data.remote.retrofit.service

import com.application.fasrecon.data.model.MsgToChatbot
import com.application.fasrecon.data.remote.response.RecommendationResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("predict")
    suspend fun classifyImage(
        @Part file: MultipartBody.Part
    ): List<List<String>>

    @POST("predict")
    suspend fun getRecommendation(
        @Body text: MsgToChatbot
    ): RecommendationResponse
}