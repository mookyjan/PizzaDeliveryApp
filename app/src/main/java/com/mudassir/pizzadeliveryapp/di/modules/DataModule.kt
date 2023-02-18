package com.mudassir.pizzadeliveryapp.di.modules

import com.mudassir.data.datasource.RemoteDataSource
import com.mudassir.data.mapper.PizzaDataToUiDataMapper
import com.mudassir.data.remote.PizzaService
import com.mudassir.data.repo.PizzaRepoImpl
import com.mudassir.domain.repo.PizzaRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    internal fun providePizzaRemoteDataSource(pizzaService: PizzaService) =
        RemoteDataSource(pizzaService)

    @Provides
    internal fun providePizzaRepository(
        remoteDataSource: RemoteDataSource,
        mapper: PizzaDataToUiDataMapper
    ): PizzaRepo =
        PizzaRepoImpl(remoteDataSource, mapper)
}


