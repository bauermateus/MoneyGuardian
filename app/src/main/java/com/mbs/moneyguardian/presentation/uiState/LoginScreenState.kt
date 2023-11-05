package com.mbs.moneyguardian.presentation.uiState

data class LoginScreenState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val isEmailFilledCorrectly: Boolean = false,
    val isPasswordFilledCorrectly: Boolean = false,
): UiState
