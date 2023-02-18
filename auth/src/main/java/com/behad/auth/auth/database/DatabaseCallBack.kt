package com.behad.auth.auth.database

import com.behad.auth.auth.model.BehadUser

interface DatabaseCallBack {
    fun onSuccess(user: BehadUser)
    fun onFailure(e: Throwable)
}
