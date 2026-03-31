package com.thematch.shared.di

import org.koin.dsl.module

actual fun platformModule() = module {
    single<String> { "http://localhost:8080/api" }
}
