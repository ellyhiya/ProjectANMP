package com.example.projectanmp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.projectanmp.databinding.FragmentLoginBinding
import com.example.projectanmp.util.FileHelper

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()
            if (username == "student" && password == "123") {
//                Toast.makeText(requireContext(),"Login Berhasil", Toast.LENGTH_SHORT).show()
                val action = LoginFragmentDirections.actionDashboardFragment()
                it.findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Username atau password salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}