package id.husni.mokat

import android.app.Application
import id.husni.mokat.core.di.databaseModule
import id.husni.mokat.core.di.networkModule
import id.husni.mokat.core.di.repositoryModule
import id.husni.mokat.di.useCaseModule
import id.husni.mokat.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApp)
            modules(listOf(
                databaseModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            ))
        }
    }
}