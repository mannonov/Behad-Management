package com.behad.auth.auth.network

import com.behad.auth.BackendErrorException
import com.behad.auth.Constans
import com.behad.auth.NotFoundException
import com.behad.auth.NotImplementedException
import com.behad.auth.Outcome
import com.behad.auth.auth.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkManager(private val userService: UserService? = null) {

    suspend fun getUserData(
        adId: String?,
        appKey: String?,
        osId: String?,
        result: (result: Outcome<User>) -> Unit,
    ) {
        try {
            result(Outcome.loading(true))
            if (userService == null) result(Outcome.failure(NotImplementedException()))
            userService?.sendToken(adId, appKey, osId)?.await()?.let { userResponse ->
                if (userResponse.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        when (userResponse.body()?.status) {
                            Constans.NOT_FOUND -> result(Outcome.failure(NotFoundException()))
                            else -> userResponse.body()?.let { result(Outcome.success(it)) }
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        result(
                            Outcome.failure(
                                BackendErrorException(
                                    userResponse.errorBody()?.string(),
                                ),
                            ),
                        )
                    }
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                result(Outcome.failure(e))
            }
        }
    }
}
