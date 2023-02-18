package com.behad.auth.auth.database

import com.behad.auth.auth.database.entitiy.UserEntitiy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseManager(private val database: UserDatabase? = null) {

    private val csScope = CoroutineScope(Dispatchers.IO)

    fun saveUser(userEntitiy: UserEntitiy) {
        csScope.launch {
            database?.getUserDao()?.insertUser(userEntitiy)
        }
    }
}
