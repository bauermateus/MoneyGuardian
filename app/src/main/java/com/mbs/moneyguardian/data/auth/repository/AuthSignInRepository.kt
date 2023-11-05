package com.mbs.moneyguardian.data.auth.repository

import com.mbs.moneyguardian.data.auth.RecoverPasswordResult
import com.mbs.moneyguardian.data.auth.SignInResult

interface AuthSignInRepository {
    suspend fun signIn(email: String, password: String): SignInResult
    suspend fun sendRecoverEmail(email:String): RecoverPasswordResult
}