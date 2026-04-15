package com.example.projectanmp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectanmp.R
import com.example.projectanmp.databinding.ActivityMainBinding
import com.example.projectanmp.databinding.FragmentDashboardBinding
import com.example.projectanmp.databinding.FragmentLoginBinding
import com.example.projectanmp.viewmodel.ListViewModel

class DashboardFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val habitListAdapter = HabitListAdapter(arrayListOf())
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
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = habitListAdapter

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.habitLD.observe(viewLifecycleOwner, Observer {
            habitListAdapter.updateList(it)
        })

    }
}