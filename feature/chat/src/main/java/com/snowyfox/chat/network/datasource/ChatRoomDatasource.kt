package com.snowyfox.chat.network.datasource

import com.snowyfox.chat.di.Constants
import com.snowyfox.chat.models.ChatRoomModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Named

class ChatRoomDatasource @Inject constructor(
    @Named(Constants.API_CLIENT) private val client: HttpClient,
    @Named(Constants.API_CHAT_ROOM_URL) private val url: String,
) {
    suspend fun getInitialChatRoom(chatId: String): ChatRoomModel {
        return client.get(url.format(chatId)).body<ChatRoomModel>()
    }
}