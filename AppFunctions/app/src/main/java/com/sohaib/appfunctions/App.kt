package com.sohaib.appfunctions

import android.app.Application
import androidx.appfunctions.service.AppFunctionConfiguration
import com.sohaib.appfunctions.appfunctions.NotesAppFunctions
import com.sohaib.appfunctions.di.appModule
import com.sohaib.appfunctions.di.databaseModule
import com.sohaib.appfunctions.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class App : Application(), AppFunctionConfiguration.Provider {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(databaseModule, repositoryModule, appModule)
        }
    }

    override val appFunctionConfiguration: AppFunctionConfiguration
        get() = AppFunctionConfiguration.Builder()
            .addEnclosingClassFactory(NotesAppFunctions::class.java) {
                NotesAppFunctions(GlobalContext.get().get())
            }
            .build()
}