package com.mudassir.domain.model

enum class PizzaSize { S, M, L }


enum class PizzaToppings { MUSHROOMS, TOMATOES, BACON, CHEESE, CHILI }

data class PizzaSizeAndPrice (val size: PizzaSize, val price: Int)

data class PizzaToppingAndPrice (val topping: PizzaToppings, val price: Int)


data class Pizza(val name: String = "",
                 val image: Int = 0,
                 var quantity: Int = 0,
                 var price: Int = 0,
                 var size: PizzaSizeAndPrice = PizzaSizeAndPrice (PizzaSize.M, 5),
                 val toppings: HashSet<PizzaToppingAndPrice> = HashSet(),
                 var resultPrice: Int? = price + size.price,
                 var imageName: String = ""
) {

    fun clone() = Pizza (name, image,quantity, price, size, hashSetOf(), resultPrice)
}