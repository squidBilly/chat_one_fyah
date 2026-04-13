package com.snowyfox.chat.repository

import com.snowyfox.chat.data.model.Messages
import com.snowyfox.chat.network.datasource.MessageSocketDatasource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessagesRepository @Inject constructor(
    private val messageSocketDatasource: MessageSocketDatasource
): IMessageRepository {
    override suspend fun getMessages(): Flow<Messages> {
       return messageSocketDatasource.connect()
    }

    override suspend fun sendMessages(messages: Messages) {
       messageSocketDatasource.sendMessage(messages)
    }

    override suspend fun disconnect() {
       messageSocketDatasource.disconnect()
    }
}