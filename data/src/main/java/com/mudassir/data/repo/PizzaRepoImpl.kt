package com.mudassir.data.repo

import android.util.Log
import com.mudassir.core.ApiResult
import com.mudassir.data.datasource.RemoteDataSource
import com.mudassir.data.mapper.PizzaDataToUiDataMapper
import com.mudassir.domain.model.Pizza
import com.mudassir.domain.repo.PizzaRepo

class PizzaRepoImpl (
    private val remoteDataSource: RemoteDataSource,
    private val mapper: PizzaDataToUiDataMapper
)  : PizzaRepo {
    override suspend fun getMenuList(): ApiResult<List<Pizza>> {
        return try {
            val response = remoteDataSource.getMenuList()
            Log.d("PizzaRepoImpl", "getMenuList: $response")
            val result = when (response) {
                is ApiResult.Success -> {
                    ApiResult.Success(response.data?.let { mapper.invoke(it) })
                }
                is ApiResult.Error -> {
                    ApiResult.Error(response.code, response.message)
                }
                is ApiResult.Exception -> {
                    ApiResult.Exception(response.throwable)
                }
                is ApiResult.Loading -> {
                    ApiResult.Loading
                }
            }
            return result

        } catch (e: Exception) {
            ApiResult.Exception(e)
        }
    }
}