package com.example.doc_di.domain.model

data class Chat(
    val id: Int,
    val email: String,
    val message: String,
    val time: String,
    val isUser: Boolean
)