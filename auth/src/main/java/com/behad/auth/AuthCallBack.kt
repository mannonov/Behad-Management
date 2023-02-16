package com.behad.auth

import com.behad.auth.auth.model.User

interface AuthCallBack {

    fun onLoading()

    fun onSuccess(user: User)

    fun onNotFoundUser(notFoundException: NotFoundException)

    fun onBackendError(e: BackendErrorException)

    fun onFailure(e: Exception)
}
