package com.mudassir.data.util

import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiResultCall<T>(proxy: Call<T>) : CallDelegate<T, ApiResult<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<ApiResult<T>>) = proxy.enqueue(object :
        Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val result = if (response.isSuccessful)
                ApiResult.Success(response.body())
            else
                ApiResult.Error(response.code(), response.message())

            callback.onResponse(this@ApiResultCall, Response.success(result))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            val result = ApiResult.Exception<T>(throwable = t)
            callback.onResponse(this@ApiResultCall, Response.success(result))
        }
    })

    override fun cloneImpl() = ApiResultCall(proxy.clone())

    override fun timeout() = Timeout()
}