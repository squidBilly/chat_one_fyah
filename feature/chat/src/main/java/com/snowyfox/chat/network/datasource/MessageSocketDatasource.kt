package com.snowyfox.chat.network.datasource

import android.util.Log
import com.snowyfox.chat.data.model.Messages
import com.snowyfox.chat.data.model.WebsocketMessageModel
import com.snowyfox.chat.di.Constants
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.converter
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.serialization.deserialize
import io.ktor.serialization.serialize
import io.ktor.websocket.CloseReason
import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.retryWhen
import okio.IOException
import javax.inject.Inject
import javax.inject.Named

class MessageSocketDatasource @Inject constructor(
    @Named(Constants.WEBSOCKET_CLIENT) private val httpClient: HttpClient,
    @Named(Constants.WEBSOCKET_URL_NAME) private val websocketUrl: String,
) {
    companion object {
        const val TAG = "MessageSocketDataSource"
        const val RETRY_DELAY = 10000L
        const val MAX_RETRIES = 6
    }

    private lateinit var webSocketSession: DefaultClientWebSocketSession
    fun connect(): Flow<Messages> = flow {
        try {
            httpClient.webSocketSession { url(websocketUrl) }
                .apply { webSocketSession = this }
                .incoming
                .receiveAsFlow()
                .collect { frame ->
                    try {
                        val message = webSocketSession.handleMessage(frame)?.toDomain()
                        if (message != null) {
                            emit(message)
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error handling WebSocket frame", e)
                    }
                }

        } catch (e: Exception) {
            Log.e(TAG, "Error, connecting to Websocket", e)

        }
    }.retryWhen { cause, attempt ->
        if (cause is IOException && attempt < MAX_RETRIES) {
            delay(RETRY_DELAY)
            true
        } else {
            false
        }
    }
    suspend fun sendMessage(messages: Messages){
        val websocketMessages = WebsocketMessageModel.fromDomain(messages)
        webSocketSession.converter?.serialize(websocketMessages)?.let {
            webSocketSession.send(it)
        }
    }

    suspend fun disconnect() {
        webSocketSession.close(CloseReason(CloseReason.Codes.NORMAL, "Disconnect"))
    }

    private suspend fun DefaultClientWebSocketSession.handleMessage(frame: Frame): WebsocketMessageModel? =
        when (frame) {
            is Frame.Text -> converter?.deserialize(frame)
            is Frame.Close -> {
                disconnect()
                null
            }

            else -> null
        }
}

