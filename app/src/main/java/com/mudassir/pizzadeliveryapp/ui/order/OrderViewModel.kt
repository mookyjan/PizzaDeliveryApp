package com.mudassir.pizzadeliveryapp.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mudassir.domain.model.Pizza
import com.mudassir.pizzadeliveryapp.model.OrderRequestData
import javax.inject.Inject

class OrderViewModel @Inject constructor(): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    var requestData = OrderRequestData()

    fun orderRequestData(pizza: List<Pizza>?, totalCost : Float) : OrderRequestData{

        requestData.customerName ="Customer Name"
        requestData.address ="Address"
        requestData.totalCost = totalCost
        requestData.pizzas = pizza

       val request = requestData.copy(customerName = null,address = null, totalCost = totalCost, pizza)
        return requestData
    }
}


