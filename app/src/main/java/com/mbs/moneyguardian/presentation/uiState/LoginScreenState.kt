package com.mbs.moneyguardian.presentation.uiState

import androidx.annotation.ColorRes
import com.mbs.moneyguardian.R

data class LoginScreenState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val isEmailFilledCorrectly: Boolean = false,
    val isPasswordFilledCorrectly: Boolean = false,
): UiState

val LoginScreenState.isLoginButtonEnabled: Boolean get() =
    isEmailFilledCorrectly && isPasswordFilledCorrectly

@get:ColorRes
val LoginScreenState.loginButtonColorRes: Int get() =
    if(isLoginButtonEnabled) R.color.primary else R.color.gray
