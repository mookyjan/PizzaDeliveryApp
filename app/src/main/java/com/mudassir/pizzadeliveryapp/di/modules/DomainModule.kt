package com.mudassir.pizzadeliveryapp.di.modules

import com.mudassir.core.dispatcher.DispatcherProvider
import com.mudassir.data.mapper.PizzaDataToUiDataMapper
import com.mudassir.domain.repo.PizzaRepo
import com.mudassir.domain.usecase.MenuListUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {
    @Provides
    @Singleton
    internal fun provideDomainMapper(dispatcherProvider: DispatcherProvider) =
        PizzaDataToUiDataMapper(dispatcherProvider = dispatcherProvider)

    @Provides
    fun provideMenuListUseCase(
        pizzaRepo: PizzaRepo,
        dispatcherProvider: DispatcherProvider
    ) = MenuListUseCase(pizzaRepo, dispatcherProvider)
}