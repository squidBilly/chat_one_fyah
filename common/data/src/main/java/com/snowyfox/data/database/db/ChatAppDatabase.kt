package com.snowyfox.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.snowyfox.data.database.entity.Conversation
import com.snowyfox.data.database.entity.Message

@Database(entities = [Message::class, Conversation::class], version = 1)
abstract class ChatAppDatabase: RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun conversationDao(): ConversationDao
}