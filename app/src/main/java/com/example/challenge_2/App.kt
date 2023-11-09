package com.example.challenge_2

import android.app.Application
import com.example.challenge_2.KoinModule.dataModule
import com.example.challenge_2.KoinModule.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            modules(
                listOf(
                    dataModule,
                    uiModule
                )
            )
        }
    }
}