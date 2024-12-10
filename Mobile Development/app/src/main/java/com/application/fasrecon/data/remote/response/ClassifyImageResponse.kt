package com.application.fasrecon.data.remote.response

data class ClassifyImageResponse(
    val label: List<Label> = emptyList()
)

data class Label(
    val color: String? = null,
    val type: String? = null
)