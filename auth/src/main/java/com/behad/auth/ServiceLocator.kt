package com.behad.auth

import android.content.Context
import com.behad.auth.auth.UserService
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {

    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    private val csScope = CoroutineScope(Dispatchers.IO)
    private val userService: UserService? by lazy { retrofit?.create(UserService::class.java) }
    private var adId: String? = null
    private var appKey: String? = null
    private var osId: String? = null

    fun init(adId: String, context: Context, baseUrl: String, appKey: String, osId: String) {
        this.adId = adId
        this.appKey = appKey
        this.osId = osId
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
    }

    fun getUserData(callBack: AuthCallBack) {
        callBack.onLoading()
        csScope.launch {
            try {
                with(userService?.sendToken(adId, appKey, osId)?.await()) {
                    if (this?.isSuccessful == true) {
                        withContext(Dispatchers.Main) {
                            when (this@with.body()?.status) {
                                404 -> callBack.onNotFoundUser(NotFoundException())
                                else -> this@with.body()?.let { callBack.onSuccess(it) }
                            }
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            callBack.onBackendError(
                                BackendErrorException(
                                    this@with?.errorBody()?.string(),
                                ),
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callBack.onFailure(e)
                }
            }
        }
    }
}
