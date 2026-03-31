package com.thematch.shared.di

import com.thematch.shared.util.Constants
import org.koin.dsl.module

actual fun platformModule() = module {
    single<String> { Constants.BASE_URL }
}
