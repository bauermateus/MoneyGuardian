package com.mbs.moneyguardian.data.auth.repository

import com.mbs.moneyguardian.data.auth.SignInResult

interface AuthSignInRepository {
    suspend fun signIn(email: String, password: String): SignInResult
}