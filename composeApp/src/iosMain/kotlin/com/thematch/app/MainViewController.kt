package com.thematch.app

import androidx.compose.ui.window.ComposeUIViewController
import com.thematch.app.di.appModule
import com.thematch.shared.di.platformModule
import com.thematch.shared.di.sharedModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        startKoin {
            modules(sharedModule, platformModule(), appModule)
        }
    }
) {
    App()
}
