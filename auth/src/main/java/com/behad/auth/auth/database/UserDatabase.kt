package com.behad.auth.auth.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.behad.auth.auth.database.convertors.UserTypeConvertors
import com.behad.auth.auth.database.dao.UserDao
import com.behad.auth.auth.database.entitiy.UserEntitiy

@Database(entities = [UserEntitiy::class], exportSchema = false, version = 1)
@TypeConverters(UserTypeConvertors::class)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        private var INSTANCE: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database",
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
