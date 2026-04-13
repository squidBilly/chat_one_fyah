package com.snowyfox.chat.repository

import com.snowyfox.chat.models.ChatRoom

interface IChatRoomRepository {
    suspend fun getInitialChatRoom(id: String): ChatRoom
}