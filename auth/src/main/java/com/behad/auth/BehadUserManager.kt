package com.behad.auth

import android.content.Context
import com.behad.auth.auth.database.DatabaseManager
import com.behad.auth.auth.database.UserDatabase
import com.behad.auth.auth.model.BehadUser
import com.behad.auth.auth.model.User
import com.behad.auth.auth.model.mapToUserEntity
import com.behad.auth.auth.network.AuthCallBack
import com.behad.auth.auth.network.NetworkManager
import com.behad.auth.auth.network.UserService
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BehadUserManager {

    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    private val csScope = CoroutineScope(Dispatchers.Main)
    private var networkManager: NetworkManager? = null
    private var databaseManager: DatabaseManager? = null
    private val userService: UserService? by lazy { retrofit?.create(UserService::class.java) }
    private var adId: String? = null
    private var appKey: String? = null
    private var osId: String? = null
    private var userDatabase: UserDatabase? = null

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
        networkManager = NetworkManager(userService = userService)
        userDatabase = UserDatabase.getInstance(context = context)
        databaseManager = DatabaseManager(userDatabase)
    }

    fun getUserData(callBack: AuthCallBack) {
        csScope.launch {
            networkManager?.getUserData(adId, appKey, osId) { result: Outcome<User> ->
                when (result) {
                    is Outcome.Success -> {
                        callBack.onSuccess(result.data)
                        result.data.userData?.let { saveUserDatabase(it) }
                    }
                    is Outcome.Failure -> {
                        when (result.e) {
                            is BackendErrorException -> callBack.onBackendError(result.e)
                            is NotFoundException -> callBack.onNotFoundUser(result.e)
                            else -> callBack.onFailure(result.e as Exception)
                        }
                    }
                    is Outcome.Progress -> {
                        callBack.onLoading()
                    }
                }
            }
        }
    }

    private fun saveUserDatabase(user: BehadUser) {
        csScope.launch(Dispatchers.IO) {
            databaseManager?.saveUser(userEntitiy = user.mapToUserEntity())
        }
    }
}
