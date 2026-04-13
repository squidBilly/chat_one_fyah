package com.snowyfox.chat.ui.screens


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.snowyfox.chat.event.ChatMessageEvent
import com.snowyfox.chat.ui.components.ListOfMessages
import com.snowyfox.chat.ui.components.SendMessageBox
import com.snowyfox.chat.ui.viewmodels.ChatViewModel
import com.snowyfox.framework.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    chatId: String?,
    onBack: () -> Unit
) {
    val messages by viewModel.messages.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.chat_title, "Alice"))
                }
            )
        },
        bottomBar = {
            SendMessageBox { viewModel.onEvent(ChatMessageEvent.SendMessage(it)) }
        }
    ) {paddingValues ->
        ListOfMessages(messages, paddingValues)
    }
}

@Composable
@Preview
fun ChatScreenPreview() {
    ChatScreen(chatId = "") { }
}