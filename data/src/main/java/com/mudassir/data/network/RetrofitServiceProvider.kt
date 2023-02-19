package com.mudassir.data.network

import com.mudassir.data.util.ApiResultAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

abstract class RetrofitServiceProvider <out T> {

   protected open fun provideRetrofit(
       baseUrl: String,
       okHttpClient: OkHttpClient,
       appCallAdapterFactory: ApiResultAdapterFactory,
       moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(appCallAdapterFactory)
            .addConverterFactory(moshiConverterFactory)
            .baseUrl(baseUrl)
            .build()
    }

    abstract fun createRetrofitService(): T

    abstract fun createMockRetrofitService(): T
}