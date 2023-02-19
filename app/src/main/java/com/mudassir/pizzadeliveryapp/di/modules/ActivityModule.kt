package com.mudassir.pizzadeliveryapp.di.modules

import com.mudassir.pizzadeliveryapp.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}