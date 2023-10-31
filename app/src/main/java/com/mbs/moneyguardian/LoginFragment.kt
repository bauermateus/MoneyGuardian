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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mbs.moneyguardian.auth.GoogleAuthUiClient
import com.mbs.moneyguardian.auth.SignInResult
import com.mbs.moneyguardian.auth.SignInViewModel
import com.mbs.moneyguardian.databinding.FragmentLoginBinding
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
                            viewModel.onSignInResult(signInResult)
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
            context = requireActivity().applicationContext,
            oneTapClient = Identity.getSignInClient(requireActivity().applicationContext)
        )
        onClick()
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startGoogleSignIn() {
        lifecycleScope.launch {
            val signInIntentSender = googleAuthUiClient.signIn()
            launcher.launch(
                IntentSenderRequest.Builder(
                    signInIntentSender ?: return@launch
                ).build()
            )
        }
    }

    private fun onClick() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.signInWithGoogle.setOnClickListener {
            startGoogleSignIn()
        }
        binding.loginButton.setOnClickListener {
            loginWithEmailAndPassword()
        }
    }

    /** This function needs to be executed before all other, this is the reason we use runBlocking. */
    private fun verifyCurrentUserState() {
        runBlocking {
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null) {
                viewModel.onSignInResult(
                    result = SignInResult(
                        username = currentUser.displayName,
                        error = null
                    )
                )
            }
        }
    }

    private fun navigate() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }

    private fun observe() {
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                startLoad(requireContext())
            } else {
                stopLoad()
            }
        }
        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.isSignInSuccessful) {
                viewModel.resetState()
                navigate()
            } else {
                state.signInError?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    viewModel.resetState()
                }
            }
        }
    }

    private fun loginWithEmailAndPassword() {
        with(binding) {
            if (email.text.isNullOrBlank()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.fill_email_field), Toast.LENGTH_SHORT
                ).show()
                return
            }
            if (password.text.isNullOrBlank()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.fill_password_field), Toast.LENGTH_SHORT
                ).show()
                return
            }
            viewModel.initLoading()
            Firebase.auth.signInWithEmailAndPassword(
                email.text.toString().trim(),
                password.text.toString().trim()
            ).addOnSuccessListener { result ->
                viewModel.onSignInResult(
                    result = SignInResult(
                        username = result.user?.displayName,
                        error = null
                    )
                )
                viewModel.keepMeLogged(binding.keepLoggedCheckbox.isChecked)
            }
        }.addOnFailureListener { exception ->
            viewModel.onSignInResult(
                result = SignInResult(
                    username = null,
                    error = exception.message
                )
            )
        }
    }
}