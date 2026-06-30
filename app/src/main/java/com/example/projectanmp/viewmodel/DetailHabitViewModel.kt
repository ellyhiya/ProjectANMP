package com.example.projectanmp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.projectanmp.model.Habit
import com.example.projectanmp.model.HabitDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailHabitViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope{

    private val job = Job()
    val habitLD = MutableLiveData<Habit>()

    fun addHabit(list:List<Habit>) {
        launch {
            val db = HabitDatabase.buildDatabase(getApplication())
            db.habitDao().insertAll(*list.toTypedArray())
        }
    }

    fun fetchHabit(id: Int) {
        launch {
            val db = HabitDatabase.buildDatabase(getApplication())
            habitLD.postValue(db.habitDao().selectHabit(id))
        }
    }

    fun updateHabit(habit: Habit) {
        launch {
            val db = HabitDatabase.buildDatabase(getApplication())
            db.habitDao().updateHabit(habit)
        }
    }

    fun deleteHabit(habit: Habit) {
        launch {
            val db = HabitDatabase.buildDatabase(getApplication())
            db.habitDao().deleteHabit(habit)
        }
    }


    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}