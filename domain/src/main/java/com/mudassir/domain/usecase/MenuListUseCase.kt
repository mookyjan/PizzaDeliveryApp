package com.mudassir.domain.usecase

import com.mudassir.core.ApiResult
import com.mudassir.core.dispatcher.DispatcherProvider
import com.mudassir.domain.UseCase
import com.mudassir.domain.model.Pizza
import com.mudassir.domain.repo.PizzaRepo
import javax.inject.Inject

class MenuListUseCase @Inject constructor(
    private val pizzaRepo: PizzaRepo,
    coroutineDispatcher: DispatcherProvider
) : UseCase<Unit, List<Pizza>>(coroutineDispatcher) {

    override suspend fun execute(param: Unit): ApiResult<List<Pizza>> {
        return pizzaRepo.getMenuList()
    }
}