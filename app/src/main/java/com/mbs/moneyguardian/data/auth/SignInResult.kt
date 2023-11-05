package com.mbs.moneyguardian.data.auth

data class SignInResult(
    val success: Boolean = false,
    val error: Exception? = null
)
