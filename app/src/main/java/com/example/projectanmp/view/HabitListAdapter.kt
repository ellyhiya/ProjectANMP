package com.example.projectanmp.view

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
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
        val idHabit = view.tag.toString().toInt()
        val action = DashboardFragmentDirections.actionEditHabitFragment(idHabit)
        Navigation.findNavController(view).navigate(action)
    }

    override fun onBtnAddClick(view: View) {
        Log.d("test", "add")
        val idHabit = view.tag.toString().toInt()
        var selectedHabit = habitList.find({habit -> habit.id == idHabit})
        listener.onBtnAddPressed(selectedHabit!!)
        notifyDataSetChanged()
    }

    override fun onBtnSubClick(view: View) {
        Log.d("test", "sub")
        val idHabit = view.tag.toString().toInt()
        var selectedHabit = habitList.find({habit -> habit.id == idHabit})
        listener.onBtnSubPressed(selectedHabit!!)
        notifyDataSetChanged()
    }
}