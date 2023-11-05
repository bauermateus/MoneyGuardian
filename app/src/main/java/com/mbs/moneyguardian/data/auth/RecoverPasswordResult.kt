package com.mbs.moneyguardian.data.auth

data class RecoverPasswordResult(
    val success: Boolean,
    val error: Exception?
)
