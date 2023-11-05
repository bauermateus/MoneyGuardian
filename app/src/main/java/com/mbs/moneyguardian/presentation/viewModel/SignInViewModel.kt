package com.mbs.moneyguardian.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbs.moneyguardian.data.auth.SignInResult
import com.mbs.moneyguardian.domain.usecase.SignInWithEmailAndPasswordUseCase
import com.mbs.moneyguardian.presentation.uiState.LoginScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase
) : ViewModel() {

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
            val result = signInWithEmailAndPasswordUseCase.invoke(email, password)
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

    fun validateEmail(email: String) {
        _state.update {
            it.copy(
                isEmailFilledCorrectly = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
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