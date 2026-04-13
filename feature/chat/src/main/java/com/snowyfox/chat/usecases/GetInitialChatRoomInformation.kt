package com.snowyfox.chat.usecases

import com.snowyfox.chat.models.ChatRoom
import com.snowyfox.chat.repository.IChatRoomRepository
import javax.inject.Inject

class GetInitialChatRoomInformation @Inject constructor(
    private val repository: IChatRoomRepository
): IChatRoomRepository{
    override suspend fun getInitialChatRoom(id: String): ChatRoom {
       return repository.getInitialChatRoom(id)
    }

}
