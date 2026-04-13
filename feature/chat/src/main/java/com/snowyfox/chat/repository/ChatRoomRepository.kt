package com.snowyfox.chat.repository

import com.snowyfox.chat.models.ChatRoom
import com.snowyfox.chat.network.datasource.ChatRoomDatasource
import javax.inject.Inject

class ChatRoomRepository @Inject constructor(
    private val datasource: ChatRoomDatasource
): IChatRoomRepository {
    override suspend fun getInitialChatRoom(id: String): ChatRoom {
       return datasource.getInitialChatRoom(id).toDomain()
    }
}