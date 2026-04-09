package com.snowyfox.chat.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ChatModule {

}

object Constants {
    const val WEBSOCKET_URL = "ws://whatspackt.com/chat/%s"
    const val WEBSOCKET_URL_NAME = "WEBSOCKET_URL"
    const val WEBSOCKET_CLIENT = "WEBSOCKET_CLIENT"
    const val API_CHAT_ROOM_URL = "http://whatspackt.com/chats/%s"
    const val API_CHAT_ROOM_URL_NAME = "CHATROOM_URL"
    const val API_CLIENT = "API_CLIENT"
}