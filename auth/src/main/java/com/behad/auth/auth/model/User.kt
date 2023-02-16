package com.behad.auth.auth.model

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Deferred
import retrofit2.Response

typealias UserResponse = Deferred<Response<User?>>

data class User(
    @SerializedName("data")
    val userData: UserData? = null,
    val message: String,
    val status: Int,
)
