package com.snowyfox.chat.di

import com.snowyfox.chat.usecases.DisconnectMessages
import com.snowyfox.chat.usecases.IDisconnectMessages
import com.snowyfox.chat.usecases.IRetrieveMessages
import com.snowyfox.chat.usecases.ISendMessages
import com.snowyfox.chat.usecases.RetrieveMessages
import com.snowyfox.chat.usecases.SendMessage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindsRetrieveMessages(
        retrieveMessages: RetrieveMessages
    ): IRetrieveMessages

    @Binds
    abstract fun bindsSendMessages(
        sendMessage: SendMessage
    ): ISendMessages

    @Binds
    abstract fun bindsDisconnectMessages(
        disconnectMessages: DisconnectMessages
    ): IDisconnectMessages
}