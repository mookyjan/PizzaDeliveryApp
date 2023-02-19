package com.mudassir.data.util

import com.mudassir.core.ApiResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class ApiResultCallAdapter<T>(
    private val type: Type,
) : CallAdapter<T, Call<ApiResult<T>>> {
    override fun responseType(): Type = type

    override fun adapt(call: Call<T>): Call<ApiResult<T>> = ApiResultCall(call)
}
