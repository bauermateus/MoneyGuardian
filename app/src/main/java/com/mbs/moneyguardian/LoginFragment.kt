package com.mbs.moneyguardian

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mbs.moneyguardian.data.auth.SignInResult
import com.mbs.moneyguardian.data.source.GoogleAuthUiClient
import com.mbs.moneyguardian.databinding.FragmentLoginBinding
import com.mbs.moneyguardian.presentation.viewModel.SignInViewModel
import com.mbs.moneyguardian.utils.extensions.toast
import com.mbs.moneyguardian.utils.startLoad
import com.mbs.moneyguardian.utils.stopLoad
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignInViewModel by viewModels()
    private lateinit var googleAuthUiClient: GoogleAuthUiClient
    private lateinit var launcher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launcher =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    result.data?.let { intent ->
                        lifecycleScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(intent = intent)
                            viewModel.onGoogleSignInResult(signInResult)
                        }
                    }
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        verifyCurrentUserState()
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        googleAuthUiClient = GoogleAuthUiClient(
            context = requireContext()
        )
        observe()
        onClick()
        validateFields()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startGoogleSignIn() {
        lifecycleScope.launch {
            val signInIntentSender = googleAuthUiClient.signIn()
            if (signInIntentSender != null) {
                launcher.launch(
                    IntentSenderRequest.Builder(
                        signInIntentSender
                    ).build()
                )
            } else {
                toast(R.string.no_account_text)
                stopLoad()
            }
        }
    }

    private fun onClick() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.signInWithGoogle.setOnClickListener {
            startLoad(requireContext())
            startGoogleSignIn()
        }
    }

    /** This function needs to be executed before all other, this is the reason we use runBlocking. */
    private fun verifyCurrentUserState() {
        runBlocking {
            val currentUser = Firebase.auth.currentUser
            currentUser?.let {
                viewModel.onGoogleSignInResult(
                    result = SignInResult(
                        success = true, error = null
                    )
                )
            }
        }
    }

    private fun navigate() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }

    private fun validateFields() {
        with(binding) {
            email.doAfterTextChanged {
                viewModel.validateEmail(it.toString().trim())
            }
            password.doAfterTextChanged {
                viewModel.validatePassword(it.toString().trim())
            }
        }
    }

    private fun observe() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.loading.collect { isLoading ->
                    if (isLoading) {
                        startLoad(requireContext())
                    } else {
                        stopLoad()
                    }
                }

            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.state.collect { state ->
                    if (state.isSignInSuccessful) {
                        navigate()
                    }
                    state.signInError?.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                    binding.loginButton.apply {
                        setBackgroundColor(
                            if (state.isEmailFilledCorrectly && state.isPasswordFilledCorrectly) {
                                ContextCompat.getColor(requireContext(), R.color.primary)
                            } else {
                                ContextCompat.getColor(requireContext(), R.color.gray)
                            }
                        )
                        setOnClickListener {
                            if (state.isEmailFilledCorrectly && state.isPasswordFilledCorrectly) {
                                viewModel.signInWithEmailAndPassword(
                                    binding.email.text.toString().trim(),
                                    binding.password.text.toString().trim()
                                )
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Preencha os campos corretamente.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}