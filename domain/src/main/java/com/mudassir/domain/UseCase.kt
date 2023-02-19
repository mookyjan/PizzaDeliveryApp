package com.mudassir.domain

import com.mudassir.core.ApiResult
import com.mudassir.core.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext

abstract class UseCase<in Input, Output>(private val coroutineDispatcher: DispatcherProvider) {

    suspend operator fun invoke(param: Input): ApiResult<Output> {
        return try {
            withContext(coroutineDispatcher.io()) {
                execute(param)
            }
        } catch (e: Exception) {
            ApiResult.Exception(e)
        }
    }

    abstract suspend fun execute(param: Input): ApiResult<Output>
}