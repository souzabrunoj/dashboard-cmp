package br.com.souzabrunoj.storekmp.di

import cafe.adriel.voyager.core.model.ScreenModel
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.OkHttpClient
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

actual inline fun <reified T : ScreenModel> Module.viewModelDefinition(
    qualifier: Qualifier?,
    noinline definition: Definition<T>,
): KoinDefinition<T> = factory(qualifier = qualifier, definition = definition)

actual fun getPlatformEngine(): HttpClientEngine  {
    return OkHttp.create { preconfigured = OkHttpClient.Builder().build() }
}