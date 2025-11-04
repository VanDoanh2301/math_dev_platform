package com.dev.devmath.core.network

import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    single { provideJson() }
//    singleOf(::DefaultTokenStorage) { bind<TokenStorage>() }
    single { provideHttpClient(get()) }
    single {
        provideKtorfit(
            httpClient = get(),
            baseUrl = NetworkConfig.BASE_URL
        )
    }
}
