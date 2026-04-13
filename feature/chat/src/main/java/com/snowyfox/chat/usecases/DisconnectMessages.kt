package com.snowyfox.chat.usecases

import com.snowyfox.chat.repository.IMessageRepository
import javax.inject.Inject

class DisconnectMessages @Inject constructor(
    private val repository: IMessageRepository
): IDisconnectMessages {
    override suspend operator fun invoke() =
        repository.disconnect()
}