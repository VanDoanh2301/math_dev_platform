package com.dev.devmath.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json



fun provideHttpClient(
    json: Json,
//    tokenStorage: TokenStorage = DefaultTokenStorage(),
    enableLogging: Boolean = NetworkConfig.ENABLE_LOGGING
): HttpClient = HttpClient {
    install(ContentNegotiation) {
        json(json)
    }

    if (enableLogging) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
            sanitizeHeader { it == HttpHeaders.Authorization }
        }
    }

    install(DefaultRequest) {
        url(NetworkConfig.BASE_URL)
        contentType(ContentType.Application.Json)
        header(HttpHeaders.Authorization, "Bearer ${NetworkConfig.BASE_TOKEN}")
    }

    install(HttpTimeout) {
        requestTimeoutMillis = NetworkConfig.TIMEOUT
        connectTimeoutMillis = NetworkConfig.CONNECT_TIMEOUT
        socketTimeoutMillis = NetworkConfig.SOCKET_TIMEOUT
    }

//    install(Auth) {
//        bearer {
//            loadTokens {
//                val accessToken = tokenStorage.getAccessToken() ?: ""
//                val refreshToken = tokenStorage.getRefreshToken() ?: ""
//                BearerTokens(
//                    accessToken = accessToken,
//                    refreshToken = refreshToken
//                )
//            }
//            refreshTokens {
//                val newAccessToken = refreshAccessToken(tokenStorage)
//                val newRefreshToken = tokenStorage.getRefreshToken() ?: ""
//                BearerTokens(
//                    accessToken = newAccessToken,
//                    refreshToken = newRefreshToken
//                )
//            }
//        }
//    }

}

//private suspend fun refreshAccessToken(tokenStorage: TokenStorage): String {
//    return tokenStorage.getAccessToken() ?: ""
//}
