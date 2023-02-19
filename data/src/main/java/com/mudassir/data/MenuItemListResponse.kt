package com.mudassir.data

import com.squareup.moshi.Json

data class MenuItemListResponse(
    @Json(name = "menuItems")
    val menuItems: List<MenuItem>? = null
)


data class MenuItem(

    @Json(name = "item_id")
    val itemId: Int? = null,

    @Json(name = "image")
    val image: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "price")
    val price: Double? = null
)