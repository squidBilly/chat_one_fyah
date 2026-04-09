package com.snowyfox.chat.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

object ChatRoomRestClient {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
    }
}