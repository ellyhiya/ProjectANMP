package com.example.projectanmp.util

import android.content.Context
import com.example.projectanmp.model.HabitDatabase
import com.example.projectanmp.model.UserDatabase

val HABIT_DB_NAME = "newHabitDB"
val USER_DB_NAME = "newUserDB"

fun buildHabitDB(context: Context): HabitDatabase {
    val db = HabitDatabase.buildDatabase(context)
    return db
}

fun buildUserDB(context: Context): UserDatabase {
    val db = UserDatabase.buildDatabase(context)
    return db
}