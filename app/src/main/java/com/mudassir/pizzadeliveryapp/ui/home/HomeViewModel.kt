package com.mudassir.pizzadeliveryapp.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.mudassir.core.ApiResult
import com.mudassir.domain.model.*
import com.mudassir.domain.usecase.MenuListUseCase
import com.mudassir.pizzadeliveryapp.R
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val menuListUseCase: MenuListUseCase) :
    ViewModel() {

    val TAG = HomeViewModel::class.java.name

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    var _currentPizzaInFocus = MutableLiveData<Pizza>()
    val currentPizzaInFocus: LiveData<Pizza> = _currentPizzaInFocus

    var _sizeSelected = MutableLiveData<PizzaSizeAndPrice>()
    val sizeSelected: LiveData<PizzaSizeAndPrice> = _sizeSelected


    var totalPrice: Int = 0
    val menuLiveDataEvent = MutableLiveData<Unit>()

    private var pizzasInCartLiveData = MutableLiveData<Int>(0)
    var pizzasInCart: LiveData<Int> = getInitialCount()

    private var _pizzasInCart: Int = 0
    private fun getInitialCount(): MutableLiveData<Int> {
        pizzasInCartLiveData.value = _pizzasInCart
        return pizzasInCartLiveData
    }

    fun getCurrentCount() {
        _pizzasInCart += 1
        pizzasInCartLiveData.value = _pizzasInCart
    }


    val pizzaImageList = intArrayOf(
        R.drawable.pizza_1_firmennaya,
        R.drawable.pizza_2_bavarska,
        R.drawable.pizza_3_margarita,
        R.drawable.pizza_4_myasna,
        R.drawable.pizza_5_po_selyanski,
        R.drawable.pizza_6_salyzmi,
        R.drawable.pizza_7_vegetarianska
    )

    /**
     * setup to get the list of menus
     */
    val menusLiveData: LiveData<ApiResult<List<Pizza>>> =
        menuLiveDataEvent.switchMap { request ->
            liveData {
                emit(ApiResult.Loading)
                val result = menuListUseCase.execute(request)
                Log.d(TAG, "liveData: $result ")
                emit(result)
            }
        }

    val pizzaToppingsList = listOf(
        PizzaToppingAndPrice(PizzaToppings.MUSHROOMS, 1),
        PizzaToppingAndPrice(PizzaToppings.TOMATOES, 2),
        PizzaToppingAndPrice(PizzaToppings.BACON, 3),
        PizzaToppingAndPrice(PizzaToppings.CHEESE, 1),
        PizzaToppingAndPrice(PizzaToppings.CHILI, 2)
    )

    val pizzaSizeList = listOf(
        PizzaSizeAndPrice(PizzaSize.S, 3),
        PizzaSizeAndPrice(PizzaSize.M, 5),
        PizzaSizeAndPrice(PizzaSize.L, 7)
    )


    fun updatePrice(price: Int, isIncrement: Boolean) {

        totalPrice = currentPizzaInFocus.value?.resultPrice ?: 0

        if (isIncrement)
            totalPrice += price
        else totalPrice -= price

        val pizza = currentPizzaInFocus.value
        pizza?.resultPrice = totalPrice

        _currentPizzaInFocus.value = pizza!!
    }

    fun updateSelectedPizza(selectedPizza: Pizza) {
        _currentPizzaInFocus.value = selectedPizza
    }

    fun updatePizzaSize(size: PizzaSizeAndPrice) {
        _sizeSelected.value = size
    }

}