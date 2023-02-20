package com.mudassir.pizzadeliveryapp.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.mudassir.core.ApiResult
import com.mudassir.domain.model.*
import com.mudassir.domain.usecase.MenuListUseCase
import com.mudassir.pizzadeliveryapp.R
import javax.inject.Inject
import kotlin.math.log

class HomeViewModel @Inject constructor(private val menuListUseCase: MenuListUseCase) :
    ViewModel() {

    val TAG = HomeViewModel::class.java.name

    var pizzaList : MutableLiveData<List<Pizza>> = MutableLiveData(listOf())

    // Get the current list of pizzas
    val currentList = pizzaList.value?.toMutableList() ?: mutableListOf()

    var currentPizza : Pizza = Pizza()

    var _currentPizzaInFocus = MutableLiveData<Pizza>()
    val currentPizzaInFocus: LiveData<Pizza> = _currentPizzaInFocus

    var _sizeSelected = MutableLiveData<PizzaSizeAndPrice>()
    val sizeSelected: LiveData<PizzaSizeAndPrice> = _sizeSelected

    var orderPrice : MutableLiveData<Int> = MutableLiveData(0)
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

//        currentPizza = currentPizzaInFocus.value?: Pizza()
        Log.d(TAG, "getCurrentCount: $currentPizza     $currentList  ${pizzaList.value}")
        // Add the new pizza to the list
        currentList.add(currentPizza)
        pizzaList.value = currentList.toList()
        Log.d(TAG, "getCurrentCount 2: $currentPizza     $currentList  ${pizzaList.value}")
//        pizzaList.value = pizzaList.value?.plus(currentPizza) ?: listOf(currentPizza)
    }

    fun updatePizza(pizza: Pizza) {
        currentPizza = pizza
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

    fun updateCartItem(cartItem: Pizza) {
        val items = pizzaList.value ?: emptyList()
        val index = items.indexOfFirst { it.name == cartItem.name }
        if (index == -1) {
            pizzaList.value = items + cartItem
        } else {
            val newList = items.toMutableList()
            newList[index] = cartItem
            pizzaList.value = newList
        }
    }

    fun calculateTotalPrice(pizzas: List<Pizza>): Int {
       var totalPrice = 0
        for (pizza in pizzas) {
            totalPrice = totalPrice?.plus(pizza.resultPrice?:0)?:0
        }
        orderPrice.postValue(totalPrice)
        return totalPrice
    }

}