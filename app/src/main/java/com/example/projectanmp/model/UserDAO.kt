package com.example.projectanmp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user:User)

    @Query("SELECT * FROM user")
    fun selectAllUser(): List<User>

    @Query("SELECT * FROM user WHERE id = :id")
    fun selectUser(id:Int): User

    @Update
    fun updateUser(user:User)

    @Query("UPDATE user SET " +
            "username=:username, " +
            "password=:password " +
            "WHERE id = :id")
    fun update(username:String, password:String, id: Int)

    @Delete
    fun deleteUser(user:User)

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    fun login(username: String, password: String): User?
}