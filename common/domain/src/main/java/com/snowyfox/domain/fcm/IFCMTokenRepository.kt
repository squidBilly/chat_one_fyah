package com.snowyfox.domain.fcm

interface IFCMTokenRepository {
    suspend fun getFCMToken(): String
}