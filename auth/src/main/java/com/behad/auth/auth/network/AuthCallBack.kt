package com.behad.auth.auth.network

import com.behad.auth.BackendErrorException
import com.behad.auth.NotFoundException
import com.behad.auth.auth.model.User

interface AuthCallBack {

    fun onLoading()

    fun onSuccess(user: User)

    fun onUserNotFound(notFoundException: NotFoundException)

    fun onBackendError(e: BackendErrorException)

    fun onFailure(e: Exception)
}
