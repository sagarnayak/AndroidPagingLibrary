package com.sagar.android.onlynetwork.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.sagar.android.logutilmaster.LogUtil
import com.sagar.android.onlynetwork.core.KeyWordsAndConstants
import com.sagar.android.onlynetwork.repository.retrofit.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule(logUtil: LogUtil) {

    var apiInterface: ApiInterface

    init {
        apiInterface = getApiInterface(
            getRetrofit(
                getOkHttpClient(
                    getHttpLoggingInterceptor(
                        logUtil
                    )
                )
            )
        )
    }

    private fun getHttpLoggingInterceptor(logUtil: LogUtil): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(
            object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    logUtil.logV(message)
                }
            }
        )
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    private fun getOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    private fun getRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(KeyWordsAndConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    private fun getApiInterface(retrofit: Retrofit) = retrofit.create(ApiInterface::class.java)
}