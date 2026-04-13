package com.snowyfox.chat.models

import com.snowyfox.chat.ui.screens.ChatScreen

data class Chat(
    val id: String? = null,
    val name: String? = null,
    val avatar: String? = null,
){
    fun ChatRoom.toUI() = run {
        Chat(
            id = this.id,
            name = this.name,
            avatar = this.avatar
        )
    }
}
