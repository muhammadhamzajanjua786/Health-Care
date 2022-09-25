package com.example.healthcare.features_news.presentation.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.healthcare.R
import com.example.healthcare.common.toast
import com.example.healthcare.databinding.FragmentLoginBinding
import com.example.healthcare.features_news.data.local.SPDatabase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var spDatabase: SPDatabase
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (spDatabase.getLogin()) {
            val email = spDatabase.getEmail().toString()
            val action = LoginFragmentDirections.navigateToDashboardFragment(email)
            findNavController().navigate(action)
        }

        binding.btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val email = binding.edtEmail.text.toString().trim()
        val pass = binding.edtPassword.text.toString().trim()

        if (email.isEmpty())
            binding.edtEmail.error = getString(R.string.email_error)
        if (pass.isEmpty())
            binding.edtPassword.error = getString(R.string.pass_error)
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            spDatabase.setLogin(true)
            spDatabase.setEmail(email)
            val action = LoginFragmentDirections.navigateToDashboardFragment(email)
            findNavController().navigate(action)
            toast(getString(R.string.login_successful))
        }
    }
}