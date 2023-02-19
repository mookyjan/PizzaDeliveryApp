package com.mudassir.domain.repo

import com.mudassir.core.ApiResult
import com.mudassir.domain.model.Pizza

interface PizzaRepo {

    suspend fun getMenuList(): ApiResult<List<Pizza>>
}