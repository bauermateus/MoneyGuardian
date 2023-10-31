package com.mbs.moneyguardian.auth

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val userName: String? = null,
    val signInError: String? = null
)