package com.snowyfox.data.di

import android.content.Context
import androidx.room.Room
import com.snowyfox.data.database.db.ChatAppDatabase
import com.snowyfox.data.database.db.ConversationDao
import com.snowyfox.data.database.db.MessageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): ChatAppDatabase =
        Room.databaseBuilder(
            context,
            ChatAppDatabase::class.java,
            "chat_database"
        ).build()

    @Provides
    fun providesMessageDao(db: ChatAppDatabase): MessageDao = db.messageDao()

    @Provides
    fun providesConversationDao(db: ChatAppDatabase): ConversationDao = db.conversationDao()
}