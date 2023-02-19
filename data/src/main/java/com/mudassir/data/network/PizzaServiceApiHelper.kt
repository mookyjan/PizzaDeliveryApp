package com.mudassir.data.network

import android.content.Context
import com.mudassir.data.remote.MockPizzaService
import com.mudassir.data.remote.PizzaService
import com.mudassir.data.util.ApiResultAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit

class PizzaServiceApiHelper(
    private val context: Context,
    private val url: String,
    private val client: OkHttpClient,
    private val appCallAdapterFactory: ApiResultAdapterFactory,
    private val moshiConverterFactory: MoshiConverterFactory
) : RetrofitServiceProvider<PizzaService>() {

    override fun createRetrofitService(): PizzaService {
        return provideRetrofit(
            baseUrl = url,
            okHttpClient = client,
            appCallAdapterFactory = appCallAdapterFactory,
            moshiConverterFactory = moshiConverterFactory
        ).create(PizzaService::class.java)
    }

    override fun createMockRetrofitService(): PizzaService {
        val retrofit = provideRetrofit(url, client, appCallAdapterFactory, moshiConverterFactory)
        val behaviour = NetworkBehavior.create()
        behaviour.setDelay(500, TimeUnit.MILLISECONDS)
        val mockRetrofit = MockRetrofit.Builder(retrofit).networkBehavior(behaviour).build()
        val delegate: BehaviorDelegate<PizzaService> = mockRetrofit.create(PizzaService::class.java)

        return MockPizzaService(delegate, context = context)
    }


}