package com.example.projectanmp.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectanmp.R
import com.example.projectanmp.databinding.ActivityMainBinding
import com.example.projectanmp.databinding.FragmentDashboardBinding
import com.example.projectanmp.databinding.FragmentLoginBinding
import com.example.projectanmp.util.FileHelper
import com.example.projectanmp.viewmodel.ListViewModel

class DashboardFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
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

        binding.fabNewHabit.setOnClickListener {
            val action = DashboardFragmentDirections.actionNewHabitFragment()
            it.findNavController().navigate(action)
        }
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        habitListAdapter = HabitListAdapter(arrayListOf(), viewModel)
        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = habitListAdapter

        observeViewModel()

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
}