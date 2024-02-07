package br.com.souzabrunoj.storekmp.di

import cafe.adriel.voyager.core.model.ScreenModel
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

expect fun getPlatformEngine(): HttpClientEngine

expect inline fun <reified T : ScreenModel> Module.viewModelDefinition(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T>