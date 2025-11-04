package com.dev.devmath.core.network

import kotlinx.serialization.json.Json

fun provideJson(): Json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    prettyPrint = false
    encodeDefaults = true
    coerceInputValues = true
    explicitNulls = false
}
