package com.behad.auth.auth

import com.behad.auth.auth.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("/api/v1/users")
    fun sendToken(
        @Query("token") tokenId: String?,
        @Query("key") key: String?,
        @Query("notification") notId: String?,
    ): UserResponse
}
