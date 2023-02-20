package com.mudassir.pizzadeliveryapp.di.modules

import com.mudassir.pizzadeliveryapp.ui.dashboard.DashboardFragment
import com.mudassir.pizzadeliveryapp.ui.home.HomeFragment
import com.mudassir.pizzadeliveryapp.ui.home.PizzaDetailFragment
import com.mudassir.pizzadeliveryapp.ui.order.OrderFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun PizzaDetailFragment(): PizzaDetailFragment

    @ContributesAndroidInjector
    abstract fun dashboardFragment(): DashboardFragment

    @ContributesAndroidInjector
    abstract fun orderFragment(): OrderFragment
}