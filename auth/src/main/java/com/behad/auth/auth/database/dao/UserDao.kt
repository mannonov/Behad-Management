package com.behad.auth.auth.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.behad.auth.auth.database.entitiy.UserEntitiy

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table LIMIT 1")
    fun getUser(): UserEntitiy

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntitiy)
}
