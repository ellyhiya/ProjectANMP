package com.example.projectanmp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectanmp.databinding.FragmentDashboardBinding
import com.example.projectanmp.model.Habit
//import com.example.projectanmp.util.FileHelper
import com.example.projectanmp.viewmodel.ListHabitViewModel

class DashboardFragment : Fragment(), HabitListButtonsListener  {
    private lateinit var viewModel: ListHabitViewModel
    private lateinit var habitListAdapter: HabitListAdapter
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListHabitViewModel::class.java)
        viewModel.refresh()
        habitListAdapter = HabitListAdapter(arrayListOf(), this)
        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = habitListAdapter

        observeViewModel()

        binding.fabNewHabit.setOnClickListener {
            val action = DashboardFragmentDirections.actionNewHabitFragment()
            it.findNavController().navigate(action)
        }

        binding.refreshLayout.setOnRefreshListener {
            binding.recView.visibility = View.GONE
            binding.progressLoad.visibility = View.VISIBLE
            viewModel.refresh()
            binding.refreshLayout.isRefreshing = false
        }
    }

    fun observeViewModel() {
        viewModel.habitLD.observe(viewLifecycleOwner, Observer {
            habitListAdapter.updateList(it)
            if(habitListAdapter.habitList.isEmpty()){
                binding.txtMessage.visibility = View.VISIBLE
                binding.txtHelp.visibility = View.VISIBLE
            } else {
                binding.txtMessage.visibility = View.GONE
                binding.txtHelp.visibility = View.GONE
            }
        })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.recView.visibility = View.GONE
                binding.progressLoad.visibility = View.VISIBLE
            } else {
                binding.recView.visibility = View.VISIBLE
                binding.progressLoad.visibility = View.GONE
            }
        })
    }
    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    override fun onBtnAddPressed(habit: Habit) {
        habit.progress += 1
        checkCompleted(habit)
        viewModel.updateHabit(habit)
    }

    override fun onBtnSubPressed(habit: Habit) {
        habit.progress -= 1
        checkCompleted(habit)
        viewModel.updateHabit(habit)
    }

    fun checkCompleted(habit: Habit){
        if (habit.progress == habit.target){
            habit.status = "Completed"
        } else habit.status = "In Progress"
    }
}