package com.application.fasrecon.data.remote.response

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(

	@field:SerializedName("recommendation_text")
	val recommendationText: String? = null,

	@field:SerializedName("outfit_recommendations")
	val outfitRecommendations: OutfitRecommendations? = null,

	@field:SerializedName("predicted_label")
	val predictedLabel: String? = null
)

data class OutfitRecommendations(

	@field:SerializedName("recommended_colors")
	val recommendedColors: List<String?>? = null,

	@field:SerializedName("recommended_outfits")
	val recommendedOutfits: List<String?>? = null
)
