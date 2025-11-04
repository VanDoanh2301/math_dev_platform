package com.dev.devmath.core.network

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class KtorfitProviderTest {

    private fun createMockHttpClient(): HttpClient {
        return HttpClient(MockEngine.create {
            addHandler { request ->
                respond(
                    content = "{}",
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
                )
            }
        })
    }

    @Test
    fun `provideKtorfit should create Ktorfit instance`() {
        val httpClient = createMockHttpClient()
        val ktorfit = provideKtorfit(httpClient)
        assertNotNull(ktorfit)
    }

    @Test
    fun `provideKtorfit should use default base URL`() {
        val httpClient = createMockHttpClient()
        val ktorfit = provideKtorfit(httpClient)
        assertEquals(NetworkConfig.BASE_URL, ktorfit.baseUrl)
    }

    @Test
    fun `provideKtorfit should use custom base URL`() {
        val customBaseUrl = "https://custom.api.com/v2/"
        val httpClient = createMockHttpClient()
        val ktorfit = provideKtorfit(httpClient, customBaseUrl)
        assertEquals(customBaseUrl, ktorfit.baseUrl)
    }

    @Test
    fun `provideKtorfit should use provided HttpClient`() {
        val httpClient = createMockHttpClient()
        val ktorfit = provideKtorfit(httpClient)
        assertNotNull(ktorfit.httpClient)
    }
}

