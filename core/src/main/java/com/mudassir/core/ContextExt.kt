package com.mudassir.core

import android.content.Context
import com.google.gson.Gson

fun <T> Context.getMockResponse(path: String, responseObj: Class<T>) : T {
    val inputStream = assets.open(path)
    val strMockResponse = inputStream.bufferedReader().use { it.readText() }
    val mockResponse = Gson().fromJson(strMockResponse, responseObj)
    inputStream.close()
    return mockResponse
}