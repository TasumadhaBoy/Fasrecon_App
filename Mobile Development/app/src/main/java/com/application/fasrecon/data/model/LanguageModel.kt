package com.application.fasrecon.data.model

import android.graphics.drawable.Drawable

data class LanguageModel (
    val id: String, val countryImage: Drawable?, val countryName: String, var checked: Boolean
)