package com.snowyfox.chat.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowyfox.chat.data.model.Messages
import com.snowyfox.chat.event.ChatMessageEvent
import com.snowyfox.chat.models.ChatMessage
import com.snowyfox.chat.models.MessageContent
import com.snowyfox.chat.usecases.IDisconnectMessages
import com.snowyfox.chat.usecases.IRetrieveMessages
import com.snowyfox.chat.usecases.ISendMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val retrieveMessages: IRetrieveMessages,
    private val sendMessage: ISendMessages,
    private val disconnectMessages: IDisconnectMessages,
) : ViewModel() {
    private var messageCollectionJob: Job? = null
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.onStart { loadAndUpdateMessages() }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            emptyList()
        )

    fun onEvent(event: ChatMessageEvent) {
        when (event) {
            is ChatMessageEvent.Disconnect -> {
                viewModelScope.launch {
                    disconnectMessages()
                }
            }

            is ChatMessageEvent.SendMessage -> {
                onSendMessage(event.message)
            }

            is ChatMessageEvent.LoadEvent -> {
                TODO()
            }
        }
    }

    private fun loadAndUpdateMessages() {
        messageCollectionJob = viewModelScope.launch(Dispatchers.IO) {
            retrieveMessages()
                .map { it.toUI() }
                .collect { message ->
                    withContext(Dispatchers.Main) {
                        _messages.value += message
                    }
                }
        }
    }

    private fun onSendMessage(messageText: String) = viewModelScope.launch(Dispatchers.IO) {
        val newMessage = _messages.value.findLast { it.isMine }
        if (newMessage == null) {
            return@launch
        }
        newMessage.copy(id = "", messageContent = MessageContent.TextMessage(messageText))
        _messages.value += newMessage
        sendMessage(newMessage.toMessages())
    }

    override fun onCleared() {
        messageCollectionJob?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            disconnectMessages
        }
    }
}

private fun Messages.toUI(): ChatMessage =
    ChatMessage(
        id = id,
        senderName = senderName,
        senderAvatar = senderName,
        timestamp = timestamp,
        isMine = isMine,
        messageContent = getMessageContent()
    )

private fun ChatMessage.getMessageContent(): Messages.ContentType =
    when (messageContent) {
        is MessageContent.TextMessage -> Messages.ContentType.TEXT
        is MessageContent.ImageMessage -> Messages.ContentType.IMAGE
    }

private fun Messages.getMessageContent(): MessageContent =
    when (contentType) {
        Messages.ContentType.TEXT -> MessageContent.TextMessage(content)
        Messages.ContentType.IMAGE -> MessageContent.ImageMessage(content, contentDescription)
    }

private fun ChatMessage.toMessages(): Messages =
    Messages(
        id = id,
        senderName = senderName,
        senderAvatar = senderAvatar,
        timestamp = timestamp,
        isMine = isMine,
        contentType = getMessageContent(),
        content = "",
        contentDescription = ""
    )