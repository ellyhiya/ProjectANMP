package com.example.projectanmp.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.projectanmp.databinding.FragmentDashboardBinding
import com.example.projectanmp.databinding.HabitCardBinding
import com.example.projectanmp.model.Habit
import com.example.projectanmp.viewmodel.ListViewModel

class HabitListAdapter(val habitList:ArrayList<Habit>, private val viewModel: ListViewModel)
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
        var progress_string = habitList[position].progress.toString() + "/" + habitList[position].limit.toString() + " " + habitList[position].unit
        var percentage = (habitList[position].progress*100) / habitList[position].limit

        with(holder.binding){
            txtHabitName.text = habitList[position].name
            txtHabitDesc.text = habitList[position].description
            chipStatus.text = habitList[position].status
            txtProgress.text = progress_string
            progressBar.progress = percentage
            imgIcon.setImageResource(habitList[position].iconId)

            btnAdd.setOnClickListener {
                habitList[position].progress += 1
                viewModel.updateList(ArrayList(habitList))
            }
            btnSub.setOnClickListener {
                habitList[position].progress += -1
                viewModel.updateList(ArrayList(habitList))
            }

            with(habitList[position]){
                if (progress == limit){
                    status = "Completed"
                    chipStatus.text = status
                    chipStatus.setChipBackgroundColorResource(android.R.color.holo_green_light)
                    chipStatus.setTextColor(Color.WHITE)
                    btnAdd.isEnabled = false
                    btnSub.isEnabled = false
                }
                if (progress == 0){
                    btnSub.isEnabled = false
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
}