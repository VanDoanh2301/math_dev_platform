package com.dev.devmath.core.network

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NetworkConfigTest {

    @Test
    fun `base URL should be correct`() {
        assertEquals("https://api.example.com/v1/", NetworkConfig.BASE_URL)
    }

    @Test
    fun `timeout values should be correct`() {
        assertEquals(30_000L, NetworkConfig.TIMEOUT)
        assertEquals(30_000L, NetworkConfig.CONNECT_TIMEOUT)
        assertEquals(30_000L, NetworkConfig.SOCKET_TIMEOUT)
    }

    @Test
    fun `logging should be enabled by default`() {
        assertTrue(NetworkConfig.ENABLE_LOGGING)
    }

    @Test
    fun `base token should be empty string by default`() {
        assertEquals("", NetworkConfig.BASE_TOKEN)
    }
}





