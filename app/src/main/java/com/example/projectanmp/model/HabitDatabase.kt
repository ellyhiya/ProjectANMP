package com.example.projectanmp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projectanmp.util.HABIT_DB_NAME

@Database(entities = arrayOf(Habit::class), version =  1)
abstract class HabitDatabase: RoomDatabase() {
    abstract fun habitDao(): HabitDAO

    companion object {
        @Volatile private var instance: HabitDatabase ?= null
        private val LOCK = Any()
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                HabitDatabase::class.java,
                HABIT_DB_NAME).build()
    }
}