package com.snowyfox.data.service

import com.snowyfox.domain.fcm.IInternalTokenRepository

class InternalTokenRepository(
    private val userId: String
) : IInternalTokenRepository {
    override suspend fun storeToken(userId: String, token: String) {
        TODO("Not yet implemented")
    }
}