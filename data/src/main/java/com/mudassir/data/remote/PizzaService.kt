package com.mudassir.data.remote

import com.mudassir.data.MenuItemListResponse
import com.mudassir.core.ApiResult
import retrofit2.http.GET

interface PizzaService {

    @GET("/menu")
    suspend fun getMenuList(): ApiResult<MenuItemListResponse>
}