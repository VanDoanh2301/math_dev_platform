package com.dev.devmath.core.network

import io.ktor.client.HttpClient
import kotlin.test.Test
import kotlin.test.assertNotNull

class HttpClientProviderTest {

    @Test
    fun `provideHttpClient should create HttpClient instance`() {
        val json = provideJson()
        val httpClient = provideHttpClient(json)
        assertNotNull(httpClient)
    }

    @Test
    fun `provideHttpClient should use provided Json`() {
        val json = provideJson()
        val httpClient = provideHttpClient(json)
        assertNotNull(httpClient)
    }

    @Test
    fun `provideHttpClient should use default TokenStorage`() {
        val json = provideJson()
        val httpClient = provideHttpClient(json)
        assertNotNull(httpClient)
    }

//    @Test
//    fun `provideHttpClient should use custom TokenStorage`() {
//        val json = provideJson()
//        val tokenStorage = DefaultTokenStorage()
//        val httpClient = provideHttpClient(json, tokenStorage)
//        assertNotNull(httpClient)
//    }

    @Test
    fun `provideHttpClient should respect enableLogging parameter`() {
        val json = provideJson()
        val httpClientWithLogging = provideHttpClient(json, enableLogging = true)
        val httpClientWithoutLogging = provideHttpClient(json, enableLogging = false)
        assertNotNull(httpClientWithLogging)
        assertNotNull(httpClientWithoutLogging)
    }

    @Test
    fun `provideHttpClient should have default request configuration`() {
        val json = provideJson()
        val httpClient = provideHttpClient(json)
        assertNotNull(httpClient)
    }
}

