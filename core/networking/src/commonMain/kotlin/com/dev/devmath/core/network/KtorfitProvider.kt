package com.dev.devmath.core.network

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient

fun provideKtorfit(
    httpClient: HttpClient,
    baseUrl: String = NetworkConfig.BASE_URL
): Ktorfit {
    return Ktorfit.Builder()
        .baseUrl(baseUrl)
        .httpClient(httpClient)
        .build()
}
