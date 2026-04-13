package com.snowyfox.chat.usecases

import com.snowyfox.chat.data.model.Messages

interface ISendMessages {
    suspend operator fun invoke(messages: Messages)
}