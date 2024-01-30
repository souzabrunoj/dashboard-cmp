package br.com.souzabrunoj.storekmp.app

import android.app.Application
import br.com.souzabrunoj.storekmp.di.commonModule
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(commonModule)
        }
    }
}