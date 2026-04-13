package com.snowyfox.chat.models

import com.snowyfox.chat.data.model.Messages

data class ChatRoom(
    val id: String,
    val name: String,
    val avatar: String,
    val lastMessage: List<Messages>
)
