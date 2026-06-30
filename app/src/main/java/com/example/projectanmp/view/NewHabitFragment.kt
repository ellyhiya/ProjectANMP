package com.example.projectanmp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.projectanmp.R
import com.example.projectanmp.databinding.FragmentNewHabitBinding
import com.example.projectanmp.model.Habit
import com.example.projectanmp.viewmodel.DetailHabitViewModel

class NewHabitFragment : Fragment() {
    private lateinit var binding: FragmentNewHabitBinding
    private lateinit var viewModel: DetailHabitViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewHabitBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailHabitViewModel::class.java]

        // setup spinner
        val items_display = arrayOf("Sport", "Study", "Gaming", "Me Time", "Creativity")
        val items_resource = arrayOf("icon_sport", "icon_book", "icon_gaming", "icon_me_time", "icon_palette")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items_display)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerIcon.adapter = adapter

        binding.btnCreate.setOnClickListener {
            with(binding){
                // setup atribut Habit baru
                val habit_name = txtHabitName.text.toString().trim()
                val desc = txtDesc.text.toString().trim()
                val goalStr = txtGoal.text.toString().trim()
                val units_used = txtUnit.text.toString().trim()
                val icon_index = spinnerIcon.selectedItemPosition // ambil posisi icon yg dipilih
                val icon_selected = items_resource[icon_index] // ambil nama file beneran e
                val imgid = resources.getIdentifier(icon_selected, "drawable", context?.packageName)
                // simpan pake ID resource e

                // cek semua sudah diisi atau blm
                if (habit_name.isEmpty() || goalStr.isEmpty() || units_used.isEmpty()) {
                    Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show()
                } else {
                    // pastikan limit goal berupa angka
                    val goal = goalStr.toIntOrNull()
                    if (goal == null) {
                        Toast.makeText(requireContext(), "Goal must be a number", Toast.LENGTH_SHORT).show()
                    } else { // aman
                        // buat objek Habit baru dri atribut2 tadi
                        val newHabit = Habit(habit_name, desc, "In Progress", 0, units_used, goal, imgid)
                        val list = listOf(newHabit)
                        viewModel.addHabit(list)
                        Toast.makeText(requireContext(), "Habit created!", Toast.LENGTH_SHORT).show()

                        // kembali ke dashboard
                        it.findNavController().popBackStack()
                    }
                }
            }
        }
    }
}