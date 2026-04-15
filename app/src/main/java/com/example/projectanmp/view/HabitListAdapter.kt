package com.example.projectanmp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.projectanmp.databinding.FragmentDashboardBinding
import com.example.projectanmp.databinding.HabitCardBinding
import com.example.projectanmp.model.Habit

class HabitListAdapter(val habitList:ArrayList<Habit>)
    : RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>()
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
        var progress = habitList[position].progress.toString() + "/" + habitList[position].limit.toString() + " " + habitList[position].unit
        var percentage = habitList[position].progress / habitList[position].limit

        with(holder.binding){
            txtHabitName.text = habitList[position].name
            txtHabitDesc.text = habitList[position].description
            chipStatus.text = habitList[position].status
            txtProgress.text = progress
            progressBar.setProgress(percentage)
            btnAdd.setOnClickListener {
                // To do
            }
            btnSub.setOnClickListener {
                // To do
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
}