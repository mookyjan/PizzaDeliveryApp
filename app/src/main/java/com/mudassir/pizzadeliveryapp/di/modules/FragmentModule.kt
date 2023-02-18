package com.mudassir.pizzadeliveryapp.di.modules

import com.mudassir.pizzadeliveryapp.ui.dashboard.DashboardFragment
import com.mudassir.pizzadeliveryapp.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun dashboardFragment(): DashboardFragment
}