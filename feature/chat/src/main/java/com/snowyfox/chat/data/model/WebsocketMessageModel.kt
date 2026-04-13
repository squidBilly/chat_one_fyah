package com.snowyfox.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WebsocketMessageModel(
    val id: String,
    val message: String,
    val senderName: String,
    val senderAvatar: String,
    val timestamp: String,
    val isMine: Boolean,
    val messageType: String,
    val messageDescription: String,
) {
    companion object {
        const val TEXT_TYPE = "TEXT"
        const val TYPE_IMAGE = "IMAGE"
    }

    fun toDomain(): Messages =
        Messages(
            id = id,
            content = message,
            senderAvatar = senderAvatar,
            senderName = senderName,
            timestamp = timestamp,
            isMine = isMine,
            contentDescription = messageDescription,
            contentType = toContentType()
        )

    fun fromDomain(messages: Messages): WebsocketMessageModel {
        return WebsocketMessageModel(
            id = messages.id,
            message = messages.content,
            senderName = messages.senderName,
            senderAvatar = messages.senderAvatar,
            timestamp = messages.timestamp,
            isMine = messages.isMine,
            messageType = messages.fromContentType(),
            messageDescription = messages.contentDescription

        )
    }

    fun toContentType(): Messages.ContentType {
        return when (messageType) {
            TEXT_TYPE -> Messages.ContentType.TEXT
            else -> Messages.ContentType.IMAGE
        }
    }
}

fun Messages.fromContentType(): String {
    return when (this.contentType) {
        Messages.ContentType.TEXT -> "TEXT"
        Messages.ContentType.IMAGE -> "IMAGE"
    }

}
