package com.example.projectanmp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectanmp.model.Habit
import com.example.projectanmp.util.FileHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListViewModel(application: Application):
    AndroidViewModel(application)
{
    val habitLD = MutableLiveData<ArrayList<Habit>>()
    val habitLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    fun refresh() {
        loadingLD.value = true
        habitLoadErrorLD.value = false

        val fileHelper = FileHelper(getApplication())
        val sType = object : TypeToken<List<Habit>>() { }.type
        val habitData = Gson().fromJson<List<Habit>>(fileHelper.readFromFile(), sType)

        habitLD.value = habitData as ArrayList<Habit>?
//        habitLD.value = arrayListOf(
//            Habit(1, "Test 1", "Description 1", "Placeholder 1", 2, "Tests", 10, "a"),
//            Habit(2, "Test 2", "Description 2", "Placeholder 2", 4, "Tests", 10, "b"),
//            Habit(3, "Test 3", "Description 3", "Placeholder 3", 6, "Tests", 10, "c"),
//            Habit(4, "Test 4", "Description 4", "Placeholder 4", 8, "Tests", 10, "d")
//        )

        habitLoadErrorLD.value = false
        loadingLD.value = false
    }

}