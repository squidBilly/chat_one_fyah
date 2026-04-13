package com.snowyfox.chat.di

import com.snowyfox.chat.network.ChatRoomRestClient
import com.snowyfox.chat.network.WebsocketClient
import com.snowyfox.chat.repository.ChatRoomRepository
import com.snowyfox.chat.repository.IChatRoomRepository
import com.snowyfox.chat.repository.IMessageRepository
import com.snowyfox.chat.repository.MessagesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
object ChatModule {
    @Provides
    @Named(Constants.WEBSOCKET_CLIENT)
    fun providesWebsocketHttpClient(): HttpClient = WebsocketClient.client

    @Provides
    @Named(Constants.WEBSOCKET_URL_NAME)
    fun providesWebsocketURL(): String = Constants.WEBSOCKET_URL

    @Provides
    @Named(Constants.API_CLIENT)
    fun providesRestClient(): HttpClient =
        ChatRoomRestClient.client

    @Provides
    @Named(Constants.API_CHAT_ROOM_URL)
    fun providesChatRoomApiUrl(): String = Constants.API_CHAT_ROOM_URL

}

@InstallIn(SingletonComponent::class)
@Module
abstract class ChatBindingModule {
    @Binds
    abstract fun bindsChatRoomRepository(
        chatroomRepository: ChatRoomRepository
    ): IChatRoomRepository

    @Binds
    abstract fun bindsMessagesRepository(
        messagesRepository: MessagesRepository
    ): IMessageRepository
}

object Constants {
    const val WEBSOCKET_URL = "ws://whatspackt.com/chat/%s"
    const val WEBSOCKET_URL_NAME = "WEBSOCKET_URL"
    const val WEBSOCKET_CLIENT = "WEBSOCKET_CLIENT"
    const val API_CHAT_ROOM_URL = "http://whatspackt.com/chats/%s"
    const val API_CHAT_ROOM_URL_NAME = "CHATROOM_URL"
    const val API_CLIENT = "API_CLIENT"
}