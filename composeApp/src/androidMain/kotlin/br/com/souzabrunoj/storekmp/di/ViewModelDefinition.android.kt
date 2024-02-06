package br.com.souzabrunoj.storekmp.di

import cafe.adriel.voyager.core.model.ScreenModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

actual inline fun <reified T : ScreenModel> Module.viewModelDefinition(
    qualifier: Qualifier?,
    noinline definition: Definition<T>,
): KoinDefinition<T> = factory(qualifier = qualifier, definition = definition)