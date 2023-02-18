package com.mudassir.data


import android.content.Context
import com.mudassir.data.network.PizzaServiceApiHelper
import com.mudassir.data.remote.PizzaService
import com.mudassir.data.util.ApiResultAdapterFactory
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {


//    @Provides
//    fun provideRetrofit(
//        baseUrl: String,
//        okHttpClient: OkHttpClient,
//        moshiConverterFactory: MoshiConverterFactory
//    ): Retrofit {
//        return Retrofit.Builder()
//            .client(okHttpClient)
//            .addConverterFactory(moshiConverterFactory)
//            .baseUrl(baseUrl)
//            .build()
//    }
//
//    @Provides
//    fun providePizzaServiceApi(retrofit: Retrofit): PizzaService =
//        retrofit.create(PizzaService::class.java)

      @Provides
    fun providePizzaServiceApi(context: Context, baseUrl: String,
                               okHttpClient: OkHttpClient,
                               appCallAdapterFactory: ApiResultAdapterFactory,
                               moshiConverterFactory: MoshiConverterFactory) : PizzaService =
        if (BuildConfig.DEBUG)
        PizzaServiceApiHelper(context,baseUrl,okHttpClient,appCallAdapterFactory, moshiConverterFactory).createMockRetrofitService()
    else PizzaServiceApiHelper(context,baseUrl, okHttpClient,appCallAdapterFactory, moshiConverterFactory).createRetrofitService()

//    @Provides
//    fun provideRetrofit(
//        baseUrl: String,
//        okHttpClient: OkHttpClient,
//        appCallAdapterFactory: ApiResultAdapterFactory,
//        moshiConverterFactory: MoshiConverterFactory
//    ): Retrofit {
//        return Retrofit.Builder()
//            .client(okHttpClient)
//            .addCallAdapterFactory(appCallAdapterFactory)
//            .addConverterFactory(moshiConverterFactory)
//            .baseUrl(baseUrl)
//            .build()
//    }


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {

        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(getHttpLoggingInterceptor())
        }

        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)
        builder.writeTimeout(60, TimeUnit.SECONDS)

        builder.addInterceptor(RequestInterceptor())

        return builder.build()
    }

    @Provides
    @Singleton
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(Wrapped.ADAPTER_FACTORY)
        .add(KotlinJsonAdapterFactory())
        .build()


    @Provides
    @Singleton
    fun getUrl(): String {
        return BuildConfig.baseUrl
    }

    @Provides
    @Singleton
    fun providesAppCallAdapterFactory(): ApiResultAdapterFactory {
        return ApiResultAdapterFactory()
    }

}

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val url = originalUrl.newBuilder()
            .build()
        //add api key here to every request
        val requestBuilder = originalRequest.newBuilder()
//            .addHeader("Authorization", BuildConfig.API_KEY)
            .url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}