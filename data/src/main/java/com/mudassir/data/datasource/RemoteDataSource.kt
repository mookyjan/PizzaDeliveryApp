package com.mudassir.data.datasource

import android.util.Log
import com.mudassir.data.MenuItemListResponse
import com.mudassir.data.remote.PizzaService
import com.mudassir.core.ApiResult

class RemoteDataSource constructor(private val pizzaService: PizzaService) {

    suspend fun getMenuList(): ApiResult<MenuItemListResponse> {
        val response = pizzaService.getMenuList()
        Log.d("TAG_RemoteDataSource", "getMenuList: $response")
        return response
    }
}