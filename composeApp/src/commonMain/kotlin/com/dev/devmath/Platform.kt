package com.dev.devmath

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform