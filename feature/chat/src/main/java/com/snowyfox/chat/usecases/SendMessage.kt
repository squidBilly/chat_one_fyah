package com.snowyfox.chat.usecases

import com.snowyfox.chat.data.model.Messages
import com.snowyfox.chat.repository.IMessageRepository
import javax.inject.Inject

class SendMessage @Inject constructor(
    private val repository: IMessageRepository
): ISendMessages {
    override suspend operator fun invoke(messages: Messages) =
        repository.sendMessages(messages)
}