package com.example.projectanmp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HabitDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg habit:Habit)

    @Query("SELECT * FROM habit")
    fun selectAllHabit(): List<Habit>

    @Query("SELECT * FROM habit WHERE id = :id")
    fun selectHabit(id:Int): Habit

    @Update
    fun updateHabit(habit:Habit)

    @Query("UPDATE habit SET " +
            "name=:name, " +
            "description=:description, " +
            "status=:status, " +
            "progress=:progress, " +
            "unit=:unit, " +
            "target=:target, " +
            "iconId=:iconId " +
            "WHERE id = :id")
    fun update(name:String, description:String, status:String, progress:Int, unit:String, target:Int, iconId:Int, id:Int)

    @Delete
    fun deleteHabit(habit:Habit)
}