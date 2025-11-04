package com.dev.devmath.core.network

interface TokenStorage {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun clearTokens()
}

class DefaultTokenStorage : TokenStorage {
    private var accessToken: String? = null
    private var refreshToken: String? = null

    override suspend fun getAccessToken(): String? = accessToken
    override suspend fun getRefreshToken(): String? = refreshToken
    override suspend fun saveAccessToken(token: String) {
        accessToken = token
    }
    override suspend fun saveRefreshToken(token: String) {
        refreshToken = token
    }
    override suspend fun clearTokens() {
        accessToken = null
        refreshToken = null
    }
}
