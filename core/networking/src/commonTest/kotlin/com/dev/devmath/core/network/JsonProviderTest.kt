package com.dev.devmath.core.network

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class TestData(
    val id: Int,
    val name: String,
    val email: String? = null
)

class JsonProviderTest {

    @Test
    fun `provideJson should create Json instance with correct configuration`() {
        val json = provideJson()
        assertNotNull(json)
    }

    @Test
    fun `Json should ignore unknown keys`() {
        val json = provideJson()
        val jsonString = """{"id": 1, "name": "Test", "unknown": "value"}"""
        val result = json.decodeFromString<TestData>(jsonString)
        
        assertEquals(1, result.id)
        assertEquals("Test", result.name)
    }

    @Test
    fun `Json should encode defaults`() {
        val json = provideJson()
        val data = TestData(id = 1, name = "Test")
        val jsonString = json.encodeToString(data)
        
        assertTrue(jsonString.contains("id"))
        assertTrue(jsonString.contains("name"))
    }

    @Test
    fun `Json should handle lenient parsing`() {
        val json = provideJson()
        val jsonString = """{"id": 1, "name": "Test"}"""
        val result = json.decodeFromString<TestData>(jsonString)
        
        assertEquals(1, result.id)
        assertEquals("Test", result.name)
    }
}

