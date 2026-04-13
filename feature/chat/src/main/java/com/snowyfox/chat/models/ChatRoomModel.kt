package com.snowyfox.chat.models

import com.snowyfox.chat.data.model.WebsocketMessageModel

data class ChatRoomModel(
    val id: String,
    val senderName: String,
    val senderAvatar: String,
    val lastMessage: List<WebsocketMessageModel>
) {
    fun toDomain(): ChatRoom =
        ChatRoom(
            id = id,
            name = senderName,
            avatar = senderAvatar,
            lastMessage = lastMessage.map { it.toDomain()}
        )
}