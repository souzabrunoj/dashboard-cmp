package br.com.souzabrunoj.storekmp.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(commonModule)
    }
}
