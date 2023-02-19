package com.mudassir.pizzadeliveryapp

import android.app.Application
import com.mudassir.pizzadeliveryapp.di.component.AppComponent
import com.mudassir.pizzadeliveryapp.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class PizzaDeliveryApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = activityDispatchingAndroidInjector

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        component = DaggerAppComponent.builder()
            .application(this)
            .context(this)
            .build()
        component.inject(this)
    }
}