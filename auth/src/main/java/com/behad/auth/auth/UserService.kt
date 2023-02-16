package com.behad.auth.auth

import com.behad.auth.auth.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("/api/v1/users")
    fun sendToken(
        @Query("token") adId: String?,
        @Query("key") appKey: String?,
        @Query("notification") osId: String?,
    ): UserResponse
}
