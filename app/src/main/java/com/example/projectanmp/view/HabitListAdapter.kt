package com.example.projectanmp.view

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectanmp.databinding.HabitCardBinding
import com.example.projectanmp.model.Habit
import com.example.projectanmp.viewmodel.ListHabitViewModel

class HabitListAdapter(val habitList:ArrayList<Habit>, val listener: HabitListButtonsListener)
    : RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>(),
    Interfaces.HabitBindingListeners
{
    class HabitViewHolder(var binding: HabitCardBinding) // buat ditampilkan ke dashboard
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitViewHolder {
        val binding = HabitCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HabitViewHolder,
        position: Int
    ) {
        Log.d("test", habitList[position].toString())
        holder.binding.habit = habitList[position]
        holder.binding.listener = this

        // setup enable/disable button di awal
//        checkStatus(holder, position)
//        var progress_string = habitList[position].progress.toString() + "/" + habitList[position].target.toString() + " " + habitList[position].unit
//        var percentage = (habitList[position].progress*100) / habitList[position].target
//
//        with(holder.binding){
//            txtName.text = habitList[position].name
//            txtHabitDesc.text = habitList[position].description
//            chipStatus.text = habitList[position].status
//            txtProgress.text = progress_string
//            progressBar.progress = percentage
//            imgIcon.setImageResource(habitList[position].iconId)

//            btnAdd.setOnClickListener {
//                habitList[position].progress += 1
//                viewModel.updateList(ArrayList(habitList))
//                checkStatus(holder, position)
//            }
//            btnSub.setOnClickListener {
//                habitList[position].progress += -1
//                viewModel.updateList(ArrayList(habitList))
//                checkStatus(holder, position)
//            }
//        }
    }

    // buat ngecek tiap kali button diklik
    fun checkStatus(holder: HabitViewHolder, position: Int){
        with(holder.binding){
            with(habitList[position]) {
                if (progress == 0) {
                    // blm mulai, buttonSub lock (ga boleh kurang)
                    btnSub.isEnabled = false
                } else if (progress == target) {
                    // sdh selesai, button lock (ga boleh ganti)
                    status = "Completed"
                    chipStatus.text = status
                    chipStatus.setChipBackgroundColorResource(android.R.color.holo_green_light)
                    chipStatus.setTextColor(Color.WHITE)
                    btnAdd.isEnabled = false
                    btnSub.isEnabled = false
                } else {
                    // in progress, button bebas
                    btnAdd.isEnabled = true
                    btnSub.isEnabled = true
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return habitList.count()
    }

    fun updateList(newHabitList: ArrayList<Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }

    override fun onTitleTextClick(view: View) {
        TODO("Not yet implemented")
    }

    override fun onBtnAddClick(view: View) {
        Log.d("test", "add")
        val idHabit = view.tag.toString().toInt()
        var selectedHabit = habitList.find({habit -> habit.id == idHabit})
        listener.onBtnAddPressed(selectedHabit!!)
    }

    override fun onBtnSubClick(view: View) {
        Log.d("test", "sub")
        val idHabit = view.tag.toString().toInt()
        var selectedHabit = habitList.find({habit -> habit.id == idHabit})
        listener.onBtnSubPressed(selectedHabit!!)
    }
}