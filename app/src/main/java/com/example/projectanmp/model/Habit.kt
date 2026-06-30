package com.example.projectanmp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Habit(
    @ColumnInfo(name="name")
    var name: String,
    @ColumnInfo(name="description")
    var description: String,
    @ColumnInfo(name="status")
    var status: String,
    @ColumnInfo(name="progress")
    var progress: Int,
    @ColumnInfo(name="unit")
    var unit: String,
    @ColumnInfo(name="target")
    var target: Int,
    @ColumnInfo(name="iconId")
    var iconId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}