package com.example.projectanmp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectanmp.model.Habit

class ListViewModel: ViewModel() {
    val habitLD = MutableLiveData<ArrayList<Habit>>()
    val habitLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()


    // DATA SEMENTARA, PLACEHOLDER BUAT COBA DISPLAY
    fun refresh() {
        loadingLD.value = true
        habitLoadErrorLD.value = false
        habitLD.value = arrayListOf(
            Habit(1, "Test 1", "Description 1", "Placeholder 1", 2, "Tests", 10, "a"),
            Habit(2, "Test 2", "Description 2", "Placeholder 2", 4, "Tests", 10, "b"),
            Habit(3, "Test 3", "Description 3", "Placeholder 3", 6, "Tests", 10, "c"),
            Habit(4, "Test 4", "Description 4", "Placeholder 4", 8, "Tests", 10, "d")
        )

        habitLoadErrorLD.value = false
        loadingLD.value = false
    }

}