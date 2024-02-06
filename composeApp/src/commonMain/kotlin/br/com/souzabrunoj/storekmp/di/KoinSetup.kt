package br.com.souzabrunoj.storekmp.di

import org.koin.core.context.startKoin

object KoinSetup {

    fun setupDi(){
        startKoin {
            modules(commonModule)
        }
    }
}