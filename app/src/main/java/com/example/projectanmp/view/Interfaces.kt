package com.example.projectanmp.view

import android.view.View
import com.example.projectanmp.model.Habit

class Interfaces {
    interface HabitBindingListeners{
        fun onTitleTextClick(view: View)
        fun onBtnAddClick(view: View)
        fun onBtnSubClick(view: View)
    }

    interface EditHabitListeners{
        fun onBtnSubmitClick(view: View)
        fun onBtnDeleteClick(view: View)
    }
}