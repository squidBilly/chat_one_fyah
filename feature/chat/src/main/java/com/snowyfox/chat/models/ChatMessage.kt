package com.snowyfox.chat.models

data class ChatMessage(
    val id: String,
    val senderName: String,
    val senderAvatar: String,
    val timestamp: String,
    val isMine: Boolean,
    val messageContent: MessageContent,
)

sealed interface MessageContent {
    data class TextMessage(val message: String) : MessageContent
    data class ImageMessage(
        val imageUrl: String,
        val contentDescription: String
    ) : MessageContent

}
