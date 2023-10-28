package com.mbs.moneyguardian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mbs.moneyguardian.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginButton()
        setupToolbar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            with(binding) {
                if (email.text.isNullOrBlank()) {
                    Toast.makeText(requireContext(),
                        getString(R.string.fill_email_field), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (password.text.isNullOrBlank()) {
                    Toast.makeText(requireContext(),
                        getString(R.string.fill_password_field), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            Firebase.auth.signInWithEmailAndPassword(email.text.toString().trim(),
                password.text.toString().trim())
                .addOnSuccessListener {  result ->
                    // TODO
                }.addOnFailureListener {
                    // TODO
                }
            }
        }
    }
}