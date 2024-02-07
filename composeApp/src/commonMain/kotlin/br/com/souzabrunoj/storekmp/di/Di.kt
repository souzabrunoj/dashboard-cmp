package br.com.souzabrunoj.storekmp.di

import br.com.souzabrunoj.storekmp.data.HomeRepositoryImpl
import br.com.souzabrunoj.storekmp.domain.repository.HomeRepository
import br.com.souzabrunoj.storekmp.presentation.screenModel.HomeViewModel
import org.koin.dsl.module

val commonModule = module {
    factory<HomeRepository> { HomeRepositoryImpl() }
    viewModelDefinition { HomeViewModel(get()) }

}