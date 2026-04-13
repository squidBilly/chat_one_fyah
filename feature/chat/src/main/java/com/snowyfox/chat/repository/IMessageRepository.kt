package com.snowyfox.chat.repository

import com.snowyfox.chat.data.model.Messages
import kotlinx.coroutines.flow.Flow

interface IMessageRepository{
    suspend fun getMessages(): Flow<Messages>
    suspend fun sendMessages(messages: Messages)
    suspend fun disconnect()
}