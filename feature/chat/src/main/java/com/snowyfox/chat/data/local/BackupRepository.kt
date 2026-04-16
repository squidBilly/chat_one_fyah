package com.snowyfox.chat.data.local

import com.google.gson.Gson
import com.snowyfox.chat.models.StorageDatasource
import com.snowyfox.data.database.db.ConversationDao
import com.snowyfox.data.database.db.MessageDao
import com.snowyfox.data.database.entity.Conversation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import java.io.File
import java.lang.System.getProperty
import javax.inject.Inject

class BackupRepository @Inject constructor(
    private val messageDao: MessageDao,
    private val conversationDao: ConversationDao,
    private val storageDatasource: StorageDatasource
) {
    private val gson = Gson()
    suspend fun backupAllConversations() {
        val conversations: Flow<List<Conversation>> =
            conversationDao.getAllConversations()
        conversations.collect {
            for (conversation in it) {
                val messages = messageDao.getMessagesInConversation(conversation.id)
                val messageJson = gson.toJson(messages)
                val tempFile = createTempFile("messages", ".json")
                tempFile.writeText(messageJson)
                val remotePath = "conversations/${conversation.id}/messages.json"
                storageDatasource.uploadFile(tempFile, remotePath)
                tempFile.delete()
            }
        }
    }
}

private fun createTempFile(prefix: String, suffix: String): File {
    val tempDir =
        File(getProperty("java.io.tmpdir") ?: "")
    return File.createTempFile(prefix, suffix, tempDir)
}