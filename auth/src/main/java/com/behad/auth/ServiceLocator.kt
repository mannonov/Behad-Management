package com.behad.auth

import android.content.Context
import android.util.Log
import com.behad.auth.auth.UserService
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {

    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null

    fun init(adId: String, context: Context, baseUrl: String, appKey: String) {
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(context))
            .addInterceptor(
                Interceptor.invoke { chain ->
                    val request = with(chain.request().newBuilder()) {
                        addHeader("Content-Type", "application/json")
                        addHeader("token", appKey)
                        addHeader("adid", adId)
                        build()
                    }
                    chain.proceed(request)
                },
            )
            .build()

        retrofit = okHttpClient?.let {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
                .client(it)
                .build()
        }

        runBlocking {
            with(getUserService()?.sendToken("adId", "rangli", "tttt")?.await()) {
                this?.let {
                    if (it.isSuccessful) {
                        Log.d("response", "onCreate: ${it.body()}")
                    }
                }
            }
        }
    }

    private fun getUserService(): UserService? {
        return retrofit?.create(UserService::class.java)
    }
}
