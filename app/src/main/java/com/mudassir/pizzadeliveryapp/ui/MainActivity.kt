package com.mudassir.pizzadeliveryapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
//import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mudassir.core.hide
import com.mudassir.core.show
import com.mudassir.pizzadeliveryapp.R
import com.mudassir.pizzadeliveryapp.databinding.ActivityMainBinding
import com.mudassir.pizzadeliveryapp.ui.home.HomeViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

         navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_order, R.id.navigation_dashboard
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        if (savedInstanceState == null) {
//            val navHostFragment =
//                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
//            setUpNavigation(navHostFragment)
//        }

        observeEvents()

        setupUi()
    }


    private fun setUpNavigation(navFragment: NavHostFragment) {
        NavigationUI.setupWithNavController(
            binding.navView,
            navFragment.navController
        )
    }

//    override fun onSupportNavigateUp() =
//        Navigation.findNavController(this, R.id.nav_host_fragment_activity_main).navigateUp()

    private fun observeEvents() {
        viewModel.pizzasInCart.observe(this, Observer {
            if (it > 0) {
                binding.cartWithBadge.tvCardBadgeCount.visibility = View.VISIBLE
            }
            binding.cartWithBadge.tvCardBadgeCount.text = "$it"
        })
    }

    fun setupUi() {
        binding.cartWithBadge.root.setOnClickListener {
//            navController.navigateUp()
            //To make the homeFragment accessible
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.navigation_home, true)
                .build()
            navController.navigate(R.id.navigation_order, null, navOptions)
        }

    }
}