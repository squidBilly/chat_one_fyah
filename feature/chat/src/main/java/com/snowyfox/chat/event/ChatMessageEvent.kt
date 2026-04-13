package com.snowyfox.chat.event

sealed interface ChatMessageEvent {
    data object LoadEvent: ChatMessageEvent
    data class SendMessage(val message: String): ChatMessageEvent
    data object Disconnect: ChatMessageEvent
}