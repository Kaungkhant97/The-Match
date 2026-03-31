package com.thematch.shared.di

import com.thematch.shared.data.remote.TheMatchApi
import com.thematch.shared.data.repository.*
import com.thematch.shared.domain.repository.*
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

val sharedModule = module {
    single<TokenStorage> { InMemoryTokenStorage() }

    single {
        val tokenStorage: TokenStorage = get()
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                level = LogLevel.BODY
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val token = tokenStorage.getToken()
                        if (token != null) BearerTokens(token, "") else null
                    }
                }
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
                connectTimeoutMillis = 15_000
            }
        }
    }

    single { TheMatchApi(get(), get<String>()) }

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<TeamRepository> { TeamRepositoryImpl(get()) }
    single<ChallengeRepository> { ChallengeRepositoryImpl(get()) }
    single<PlaceRepository> { PlaceRepositoryImpl(get()) }
}

expect fun platformModule(): Module
