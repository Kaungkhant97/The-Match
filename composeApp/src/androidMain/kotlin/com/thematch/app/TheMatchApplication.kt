package com.thematch.app

import android.app.Application
import com.thematch.app.di.appModule
import com.thematch.shared.di.platformModule
import com.thematch.shared.di.sharedModule
import org.koin.core.context.startKoin

class TheMatchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(sharedModule, platformModule(), appModule)
        }
    }
}
