package com.application.fasrecon.data.remote.retrofit

import com.application.fasrecon.data.remote.response.ClassifyImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("predict")
    suspend fun classifyImage(
        @Part file: MultipartBody.Part
    ): List<List<String>>
}