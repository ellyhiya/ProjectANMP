package com.example.projectanmp.view

import android.graphics.Color
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip


@BindingAdapter("imageResource")
fun loadIcon(imageView: ImageView, iconId:Int){
    imageView.setImageResource(iconId)
}

@BindingAdapter("progressPercentage", "targetPercentage")
fun setProgressPercentage(progressBar: ProgressBar, progress: Int, target: Int){
    progressBar.progress = (progress*100) / target
}

@BindingAdapter("progressText", "targetText", "unitText")
fun setProgressText(view: TextView, progress: Int, target: Int, unit: String){
    var progress_string = progress.toString() + "/" + target.toString() + " " + unit
    view.text = progress_string
}

@BindingAdapter("setHabitStatus")
fun setStatus(chip: Chip, status: String){
    chip.text = status
    if (status == "Completed"){
        chip.setChipBackgroundColorResource(android.R.color.holo_green_light)
        chip.setTextColor(Color.WHITE)
    }
}