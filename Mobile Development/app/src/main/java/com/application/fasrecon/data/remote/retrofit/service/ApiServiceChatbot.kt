package com.application.fasrecon.data.remote.retrofit.service

import com.application.fasrecon.data.model.MsgToChatbot
import com.application.fasrecon.data.remote.response.RecommendationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceChatbot {

    @POST("predict")
    suspend fun getRecommendation(
        @Body text: MsgToChatbot
    ): RecommendationResponse
}