package com.example.projectanmp.view

import com.example.projectanmp.model.Habit

interface HabitListButtonsListener {
    fun onBtnAddPressed(habit: Habit)
    fun onBtnSubPressed(habit: Habit)
}