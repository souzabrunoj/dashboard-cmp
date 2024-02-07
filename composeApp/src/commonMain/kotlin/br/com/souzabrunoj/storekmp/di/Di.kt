package br.com.souzabrunoj.storekmp.di

import br.com.souzabrunoj.storekmp.data.HomeRepositoryImpl
import br.com.souzabrunoj.storekmp.networking.networkModule
import br.com.souzabrunoj.storekmp.domain.repository.HomeRepository
import br.com.souzabrunoj.storekmp.presentation.screenModel.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import org.koin.dsl.module

val commonModule = module {
    factory<HomeRepository> { HomeRepositoryImpl(get()) }
    viewModelDefinition { HomeViewModel(get()) }
    single<HttpClientEngine> { getPlatformEngine() }
    single<HttpClient> { networkModule("fakestoreapi.com", get()) }
}