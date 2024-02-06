package br.com.souzabrunoj.storekmp.di

import cafe.adriel.voyager.core.model.ScreenModel
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

expect inline fun <reified T : ScreenModel> Module.viewModelDefinition(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T>