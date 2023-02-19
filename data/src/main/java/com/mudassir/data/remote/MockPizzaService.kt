package com.mudassir.data.remote

import android.content.Context
import com.mudassir.core.ApiResult
import com.mudassir.core.getMockResponse
import com.mudassir.data.MenuItemListResponse
import retrofit2.mock.BehaviorDelegate

class MockPizzaService(
    private val delegate: BehaviorDelegate<PizzaService>,
    private val context: Context
) : PizzaService {
    override suspend fun getMenuList(): ApiResult<MenuItemListResponse> {

        val mockResponse = context.getMockResponse("mock/getPizzaListApi.json", MenuItemListResponse::class.java)

        return delegate.returningResponse(mockResponse).getMenuList()

    }


//    fun <T> getMockData(context: Context, file:String, responseObj: Class<T>) : T {
//        val stream : InputStream? = context.assets?.open(file)
//        val jsonString = s
//    }


}