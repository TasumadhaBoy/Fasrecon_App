package com.application.fasrecon.data.model

class ChatMessage (
    val id: String,
    val messages: String,
    val photoUrl: String,
    val listPhoto: List<String> = emptyList()
)