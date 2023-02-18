package com.mudassir.pizzadeliveryapp.di.modules

import android.app.Application
import android.content.Context
import com.mudassir.core.dispatcher.DefaultDispatcherProvider
import com.mudassir.core.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @Named("application.Context")
    fun provideContext(application: Application) : Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): DispatcherProvider = DefaultDispatcherProvider()

}