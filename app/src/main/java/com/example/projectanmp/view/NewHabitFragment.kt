package com.example.projectanmp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.projectanmp.R
import com.example.projectanmp.databinding.FragmentNewHabitBinding
import com.example.projectanmp.model.Habit
import com.example.projectanmp.util.FileHelper
import com.example.projectanmp.viewmodel.DetailViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NewHabitFragment : Fragment() {
    private lateinit var binding: FragmentNewHabitBinding
    private lateinit var viewModel: DetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewHabitBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch()

        //spinner setup(ga yakin)
        val icons = listOf("basketball", "study", "esports", "painting", "swimming")

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            icons
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerIcon.adapter = adapter

        binding.btnCreate.setOnClickListener {

            val name = binding.txtHabitName.editText?.text.toString().trim()
            val desc = binding.txtDesc.editText?.text.toString().trim()
            val goalStr = binding.txtGoal.editText?.text.toString().trim()
            val unit = binding.txtUnit.editText?.text.toString().trim()
            val icon = binding.spinnerIcon.selectedItem.toString()

            if (name.isEmpty() || desc.isEmpty() || goalStr.isEmpty() || unit.isEmpty()) {
                Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT)
                    .show()
            }
            val goal = goalStr.toIntOrNull()
            if (goal == null) {
                Toast.makeText(requireContext(), "Goal must be a number", Toast.LENGTH_SHORT).show()
            }
            val fileHelper = FileHelper(requireContext())
            val json = fileHelper.readFromFile()

            val type = object : TypeToken<ArrayList<Habit>>() {}.type

            val habitList: ArrayList<Habit> =
                if (json.isEmpty()) ArrayList()
                else Gson().fromJson(json, type)
            val newHabit = Habit(
                id = habitList.size + 1,
                name = name,
                description = desc,
                status = "In Progress",
                progress = 0,
                unit = unit,
                limit = goal!!,
                icon = icon
            )

            habitList.add(newHabit)

            //save
            val newJson = Gson().toJson(habitList)
            fileHelper.writeToFile(newJson)

            Toast.makeText(requireContext(), "Habit created!", Toast.LENGTH_SHORT).show()
            //back to dashboard
            findNavController().popBackStack() //bole ga nieh
        }

    }
}