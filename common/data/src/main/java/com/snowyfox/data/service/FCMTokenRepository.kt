package com.snowyfox.data.service

import javax.inject.Inject

class FCMTokenRepository @Inject constructor(
    private val tokenDatasource: FCMTokenDataSource
) {
    suspend fun getToken(): String?{
        return tokenDatasource.getFcmToken()
    }
}