package com.mudassir.pizzadeliveryapp.model

import com.mudassir.domain.model.Pizza
import com.squareup.moshi.Json


data class OrderRequestData(

    @Json(name = "customer_name")
    var customerName: String? = null,

    @Json(name = "address")
    var address: String? = null,

    @Json(name = "price")
    var totalCost: Float? = 0f,

    @Json(name = "pizzas")
    var pizzas: List<Pizza>? = null
)