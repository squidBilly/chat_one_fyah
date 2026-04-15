package com.snowyfox.chat.models

import com.snowyfox.framework.utils.map

data class Chat(
    val id: String? = null,
    val name: String? = null,
    val avatar: String? = null,
) {
    companion object {
        fun Chat.toChatRoom(): ChatRoom {
            return map {
                ChatRoom(
                    id = it.id ?: "",
                    name = it.name ?: "",
                    avatar = it.avatar ?: "",
                    lastMessage = emptyList()
                )
            }
        }
    }
}

fun ChatRoom.toUI() = run {
    Chat(
        id = this.id,
        name = this.name,
        avatar = this.avatar
    )
}

