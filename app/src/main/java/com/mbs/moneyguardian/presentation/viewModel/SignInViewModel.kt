package com.mbs.moneyguardian.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbs.moneyguardian.data.auth.SignInResult
import com.mbs.moneyguardian.data.auth.repository.AuthSignInRepository
import com.mbs.moneyguardian.presentation.uiState.LoginScreenState
import com.mbs.moneyguardian.utils.extensions.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val authSignInRepository: AuthSignInRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(LoginScreenState())
    val state = _state.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun onGoogleSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.error == null,
                signInError = result.error?.message
            )
        }
        _loading.update { false }
    }

    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.update { true }
            val result = authSignInRepository.signIn(email, password)
            if (result.success) {
                _state.update {
                    it.copy(
                        isSignInSuccessful = true,
                        signInError = null
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        isSignInSuccessful = false,
                        signInError = result.error?.message
                    )
                }
            }
            _loading.update { false }
        }
    }

//    private fun validateSignInButton() {
//        _state.update {
//            val isFilledCorrectly = it.isPasswordFilledCorrectly && it.isEmailFilledCorrectly
//            it.copy(
//                isLoginButtonEnabled = isFilledCorrectly,
//                loginButtonColorRes = if (isFilledCorrectly) R.color.primary else R.color.gray
//            )
//        }
//    }

    fun validateEmail(email: String) {
        _state.update {
            it.copy(
                isEmailFilledCorrectly = email.isValidEmail()
            )
        }
    }

    fun validatePassword(password: String) {
        _state.update {
            it.copy(
                isPasswordFilledCorrectly = password.length > 5
            )
        }
    }
}