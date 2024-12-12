package com.application.fasrecon.data.remote.retrofit

import com.application.fasrecon.data.remote.response.ClassifyImageResponse
import com.application.fasrecon.data.remote.response.RecommendationResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @Multipart
    @POST("predict")
    suspend fun classifyImage(
        @Part file: MultipartBody.Part
    ): List<List<String>>

    @POST("predict")
    suspend fun getRecommendation(
        @Path("text") text: String
    ): RecommendationResponse
}