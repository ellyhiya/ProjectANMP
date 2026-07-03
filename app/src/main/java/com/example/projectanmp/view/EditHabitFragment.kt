package com.example.projectanmp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.projectanmp.R
import com.example.projectanmp.databinding.FragmentEditHabitBinding
import com.example.projectanmp.databinding.FragmentNewHabitBinding
import com.example.projectanmp.model.Habit
import com.example.projectanmp.viewmodel.DetailHabitViewModel

class EditHabitFragment : Fragment(), Interfaces.EditHabitListeners {
    private lateinit var binding: FragmentEditHabitBinding
    private lateinit var viewModel: DetailHabitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = EditHabitFragmentArgs.fromBundle(requireArguments()).id
        viewModel = ViewModelProvider(this)[DetailHabitViewModel::class.java]
        viewModel.fetchHabit(id)
        observeViewModel()

        // setup spinner
        val items_display = arrayOf("Sport", "Study", "Gaming", "Me Time", "Creativity")
        val items_resource = arrayOf("icon_sport", "icon_book", "icon_gaming", "icon_me_time", "icon_palette")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items_display)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerIcon.adapter = adapter
    }


    fun observeViewModel(){
        viewModel.habitLD.observe(viewLifecycleOwner, Observer {
            Log.d("test", it.toString())
            binding.habit = it
            val position = when (it.iconId) {
                R.drawable.icon_sport -> 0
                R.drawable.icon_book -> 1
                R.drawable.icon_gaming -> 2
                R.drawable.icon_me_time -> 3
                R.drawable.icon_palette -> 4
                else -> 0
            }

            binding.spinnerIcon.setSelection(position)

            binding.listener = this
        })
    }

    override fun onBtnSubmitClick(view: View) {
        binding.habit!!.iconId =
            when (binding.spinnerIcon.selectedItemPosition) {
                0 -> R.drawable.icon_sport
                1 -> R.drawable.icon_book
                2 -> R.drawable.icon_gaming
                3 -> R.drawable.icon_me_time
                else -> R.drawable.icon_palette
            }
        viewModel.updateHabit(binding.habit!!)

        Toast.makeText(requireContext(), "Habit updated!", Toast.LENGTH_SHORT).show()
        // kembali ke dashboard
        Navigation.findNavController(view).popBackStack()
    }

    override fun onBtnDeleteClick(view: View) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Habit Data")
        builder.setMessage("Are you sure you want to delete this habit?")
        builder.setPositiveButton("Delete") { dialog, _ ->
            viewModel.deleteHabit(binding.habit!!)
            Toast.makeText(requireContext(), "Habit deleted!", Toast.LENGTH_SHORT).show()
            // kembali ke dashboard
            Navigation.findNavController(view).popBackStack()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }
}