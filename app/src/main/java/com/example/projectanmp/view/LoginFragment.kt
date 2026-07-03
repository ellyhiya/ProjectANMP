package com.example.projectanmp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.projectanmp.databinding.FragmentLoginBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.projectanmp.util.SessionManager
import com.example.projectanmp.viewmodel.LoginViewModel
//import com.example.projectanmp.util.FileHelper

class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
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
        if(SessionManager(requireContext()).isLogin()){
            val action = LoginFragmentDirections.actionDashboardFragment()
            findNavController().navigate(action)
            return
        }
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.btnLogin.setOnClickListener {
//            val username = binding.txtUsername.text.toString()
//            val password = binding.txtPassword.text.toString()
//            if (username == "student" && password == "123") {
//            if (username == "" && password == "") {
////                Toast.makeText(requireContext(),"Login Berhasil", Toast.LENGTH_SHORT).show()
//                val action = LoginFragmentDirections.actionDashboardFragment()
//                it.findNavController().navigate(action)
//            } else {
//                Toast.makeText(requireContext(), "Username atau password salah", Toast.LENGTH_SHORT).show()
//            }
            viewModel.login(
                binding.txtUsername.text.toString(),
                binding.txtPassword.text.toString()
            )
        }
        viewModel.loginResultLD.observe(viewLifecycleOwner) { user ->
            if(user != null){
                SessionManager(requireContext()).setLogin(true)

                findNavController().navigate(
                    LoginFragmentDirections.actionDashboardFragment()
                )
            }else{
                Toast.makeText(
                    requireContext(),
                    "Username atau password salah",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}