package com.example.projectanmp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.projectanmp.databinding.FragmentLoginBinding

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
            val username=binding.editUsername.text.toString().trim()
            val password=binding.editPsw.text.toString().trim()
            if (username == "student" && password == "123") {
                Toast.makeText(requireContext(),"Login Berhasil", Toast.LENGTH_SHORT).show()

                val actionLoginToDashboard= LoginFragmentDirections.actionLoginToDashboard()
                findNavController().navigate(actionLoginToDashboard)

            } else {
                Toast.makeText(requireContext(), "Username atau password salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}