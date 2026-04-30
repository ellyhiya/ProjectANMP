package com.example.projectanmp.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.application
import androidx.navigation.findNavController
import com.example.projectanmp.model.Habit
import com.example.projectanmp.util.FileHelper
import com.example.projectanmp.view.NewHabitFragmentDirections
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson

class ListViewModel(application: Application):
    AndroidViewModel(application)
{
    val habitLD = MutableLiveData<ArrayList<Habit>>()
    val habitLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val fileHelper = FileHelper(getApplication())
    fun refresh() {
        loadingLD.value = true
        habitLoadErrorLD.value = false

        val jsonData = fileHelper.readFromFile()
        val sType = object: TypeToken<ArrayList<Habit>>() {}.type
        var habitList: ArrayList<Habit> = ArrayList()
        if (!jsonData.isEmpty()) {
            Log.d("data_file_read_refresh", jsonData)
            habitList = Gson().fromJson(jsonData, sType)
            habitLD.value = habitList
        } else habitLD.value = arrayListOf()

        habitLoadErrorLD.value = false
        loadingLD.value = false
    }

    fun updateList(updatedList: ArrayList<Habit>){
        loadingLD.value = true
        habitLoadErrorLD.value = false

        updatedList.forEach { habit ->
            if (habit.progress == habit.limit) {
                habit.status = "Completed"
            } else {
                habit.status = "In Progress"
            }
        }

        val newJson = Gson().toJson(updatedList)
        Log.d("print_file_write", newJson)
        fileHelper.writeToFile(newJson)

        habitLD.value = updatedList
        habitLoadErrorLD.value = false
        loadingLD.value = false
    }
}