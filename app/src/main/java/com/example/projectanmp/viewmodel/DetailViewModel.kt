package com.example.projectanmp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectanmp.model.Habit

class DetailViewModel : ViewModel(){
    val habitLD = MutableLiveData<Habit>()
    fun fetch() {
        //val student1 = Habit(1,"","","",
          //  9);
        //habitLD.value = habit1
    }
}