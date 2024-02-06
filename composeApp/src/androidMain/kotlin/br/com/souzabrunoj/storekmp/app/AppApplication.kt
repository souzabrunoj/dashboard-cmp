package br.com.souzabrunoj.storekmp.app

import android.app.Application
import br.com.souzabrunoj.storekmp.di.KoinSetup

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinSetup.setupDi()
    }
}