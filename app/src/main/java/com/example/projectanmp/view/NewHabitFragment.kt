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
import com.example.projectanmp.util.FileHelper
//import com.example.projectanmp.viewmodel.DetailViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NewHabitFragment : Fragment() {
    private lateinit var binding: FragmentNewHabitBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewHabitBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setup spinner
        val items_display = arrayOf("Sport", "Study", "Gaming", "Me Time", "Creativity")
        val items_resource = arrayOf("icon_sport", "icon_book", "icon_gaming", "icon_me_time", "icon_palette")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items_display)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerIcon.adapter = adapter

        // baca data yg sdh ada di json file (kalo ada)
        var current_id = 0
        val fileHelper = FileHelper(requireContext())
        val jsonData = fileHelper.readFromFile()
        val type = object : TypeToken<ArrayList<Habit>>() {}.type
        var habitList: ArrayList<Habit> = ArrayList()
        if (!jsonData.isEmpty()) {
            Log.d("data_file_read", jsonData) //debug bntr
            habitList = Gson().fromJson(jsonData, type)
            current_id = habitList.size
            Log.d("data_file_last_id", "ID sekarang: $current_id") //debug bntr
        }

        binding.btnCreate.setOnClickListener {
            with(binding){
                // setup atribut Habit baru
                val habit_name = txtHabitName.editText?.text.toString().trim()
                val desc = txtDesc.editText?.text.toString().trim()
                val goalStr = txtGoal.editText?.text.toString().trim()
                val units_used = txtUnit.editText?.text.toString().trim()
                val icon_index = spinnerIcon.selectedItemPosition // ambil posisi icon yg dipilih
                val icon_selected = items_resource[icon_index] // ambil nama file beneran e
                val imgid = resources.getIdentifier(icon_selected, "drawable", context?.packageName)
                // simpan pake ID resource e

                // cek semua sudah diisi atau blm
                if (habit_name.isEmpty() || desc.isEmpty() || goalStr.isEmpty() || units_used.isEmpty()) {
                    Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show()
                } else {
                    // pastikan limit goal berupa angka
                    val goal = goalStr.toIntOrNull()
                    if (goal == null) {
                        Toast.makeText(requireContext(), "Goal must be a number", Toast.LENGTH_SHORT).show()
                    } else { //aman
                        // buat objek Habit baru dri atribut2 tadi
                        val newHabit = Habit(current_id, habit_name, desc, "In Progress", 0, units_used, goal, imgid)

                        // isi ke habitList
                        habitList.add(newHabit)

                        // save ke file dlm bntk json
                        val newJson = Gson().toJson(habitList)
                        fileHelper.writeToFile(newJson)
                        Log.d("data_file_write_new", newJson)
                        Toast.makeText(requireContext(), "Habit created!", Toast.LENGTH_SHORT).show()

                        // pindah kembali ke dashboard
                        val action = NewHabitFragmentDirections.actionDashboardFragmentFromHabit()
                        it.findNavController().navigate(action)
                    }
                }
            }
        }

        binding.btnDel.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Delete All Habits")
            builder.setMessage("Are you sure you want to delete all habits?")
            builder.setPositiveButton("Delete") { dialog, _ ->
                val fileHelper = FileHelper(requireContext())
                fileHelper.deleteFile()
                dialog.dismiss()
                val action = NewHabitFragmentDirections.actionDashboardFragmentFromHabit()
                it.findNavController().navigate(action)
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            builder.create().show()
        }
    }
}