package com.snowyfox.chat.usecases

import com.snowyfox.chat.data.model.Messages
import com.snowyfox.chat.repository.IMessageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveMessages @Inject constructor(
    private val repository: IMessageRepository
) : IRetrieveMessages{
    override suspend operator fun invoke(): Flow<Messages> =
        repository.getMessages()
}