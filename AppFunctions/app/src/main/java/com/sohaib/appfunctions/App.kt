package com.sohaib.appfunctions

import android.app.Application
import com.sohaib.appfunctions.di.appModule
import com.sohaib.appfunctions.di.databaseModule
import com.sohaib.appfunctions.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(databaseModule, repositoryModule, appModule)
        }
    }
}