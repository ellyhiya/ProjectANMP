package com.example.projectanmp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projectanmp.util.USER_DB_NAME

@Database(entities = arrayOf(User::class), version =  1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDAO

    companion object {
        @Volatile private var instance: UserDatabase ?= null
        private val LOCK = Any()
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                USER_DB_NAME).build()
    }
}