package com.snowyfox.conversation.model

import androidx.annotation.StringRes
import com.snowyfox.framework.R
import com.snowyfox.framework.R.string

data class ConversationsListTab(
    @StringRes val title: Int
)
fun generateTabs(): List<ConversationsListTab>{
    return listOf(
        ConversationsListTab(
            title = R.string.conversations_tab_calls_title,
        ),
        ConversationsListTab(
            title = R.string.conversations_tab_chats_title,
        ),
        ConversationsListTab(
            title = R.string.conversations_tab_calls_title,
        ),
    )
}

