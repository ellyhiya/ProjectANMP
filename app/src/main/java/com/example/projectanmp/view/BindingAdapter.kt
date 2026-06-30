package com.example.projectanmp.view

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
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
    } else {
        chip.setChipBackgroundColorResource(android.R.color.system_on_secondary_fixed)
        chip.setTextColor(Color.WHITE)
    }
}

@BindingAdapter("checkProgressMax", "checkTargetMax")
fun disableAdd(btn: Button, progress: Int, target: Int){
    if (progress == target){
        btn.isEnabled = false
    } else {
        btn.isEnabled = true
    }
}

@BindingAdapter("checkProgressMin", "checkTargetMin")
fun disableSub(btn: Button, progress: Int, target: Int){
    if (progress == 0){
        btn.isEnabled = false
    } else {
        btn.isEnabled = true
    }
}

// ================ AI SECTION  =================
@BindingAdapter("android:text")
fun setText(view: EditText, value: Int?) {
    val newText = value?.toString() ?: ""
    if (view.text.toString() != newText) {
        view.setText(newText)
    }
}

@InverseBindingAdapter(attribute = "android:text")
fun getText(view: EditText): Int {
    return view.text.toString().toIntOrNull() ?: 0
}

@BindingAdapter("android:textAttrChanged")
fun setTextWatcher(view: EditText, attrChange: InverseBindingListener) {
    view.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            attrChange.onChange()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

// ================ AI SECTION  =================