package com.example.projectanmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.application
import com.example.projectanmp.model.Habit
import com.example.projectanmp.model.HabitDatabase
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListHabitViewModel(application: Application):
    AndroidViewModel(application), CoroutineScope
{
    val habitLD = MutableLiveData<ArrayList<Habit>>()
    val habitLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()
    fun refresh() {
        loadingLD.value = true
        habitLoadErrorLD.value = false
        launch {
            val db = HabitDatabase.buildDatabase(
                getApplication()
            )
            habitLD.postValue(db.habitDao().selectAllHabit() as ArrayList<Habit>)
            loadingLD.postValue(false)
        }
        habitLoadErrorLD.value = false
        loadingLD.value = false
    }

    fun updateHabit(habit: Habit) {
        launch {
            val db = HabitDatabase.buildDatabase(getApplication())
            db.habitDao().updateHabit(habit)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}