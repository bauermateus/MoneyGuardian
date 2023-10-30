package com.mbs.moneyguardian

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mbs.moneyguardian.databinding.FragmentLoginBinding
import com.mbs.moneyguardian.utils.Constants
import com.mbs.moneyguardian.utils.DataStoreManager
import com.mbs.moneyguardian.utils.startLoad
import com.mbs.moneyguardian.utils.stopLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
//    private val preferences = DataStoreManager(requireContext())

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
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.fill_email_field), Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                if (password.text.isNullOrBlank()) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.fill_password_field), Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                startLoad(requireContext())
                Firebase.auth.signInWithEmailAndPassword(
                    email.text.toString().trim(),
                    password.text.toString().trim()
                ).addOnSuccessListener { result ->
                    lifecycleScope.launch(Dispatchers.IO) {
                        DataStoreManager(requireContext()).putBoolean(
                            Constants.KEEP_ME_LOGGED,
                            binding.keepLoggedCheckbox.isChecked
                        )
                    }
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }.addOnFailureListener { exception ->
                    stopLoad()
                    Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}