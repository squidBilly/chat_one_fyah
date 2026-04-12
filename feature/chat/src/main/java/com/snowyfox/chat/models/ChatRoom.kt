package com.snowyfox.chat.models

data class ChatRoom(
    val id: String,
    val name: String,
    val avatar: String,
    val lastMessage: List<Messages>
)
