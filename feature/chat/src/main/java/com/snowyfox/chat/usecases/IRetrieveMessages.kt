package com.snowyfox.chat.usecases

import com.snowyfox.chat.data.model.Messages
import kotlinx.coroutines.flow.Flow

interface IRetrieveMessages {
    suspend operator fun invoke(): Flow<Messages>
}