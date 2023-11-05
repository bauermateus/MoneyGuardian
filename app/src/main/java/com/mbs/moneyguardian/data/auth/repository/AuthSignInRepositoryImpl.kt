package com.mbs.moneyguardian.data.auth.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.auth
import com.mbs.moneyguardian.data.auth.RecoverPasswordResult
import com.mbs.moneyguardian.data.auth.SignInResult
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthSignInRepositoryImpl @Inject constructor() : AuthSignInRepository {

    override suspend fun signIn(email: String, password: String): SignInResult {
        return suspendCoroutine { continuation ->
            Firebase.auth.signInWithEmailAndPassword(
                email,
                password
            ).addOnSuccessListener { result ->
                continuation.resume(
                    SignInResult(
                        success = result.user != null,
                        error = null
                    )
                )
            }.addOnFailureListener { exception ->
                continuation.resume(
                    SignInResult(
                        success = false,
                        error = exception
                    )
                )
            }
        }
    }

    override suspend fun sendRecoverEmail(email: String): RecoverPasswordResult =
        suspendCoroutine { continuation ->
            Firebase.auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    continuation.resume(
                        RecoverPasswordResult(
                            success = true,
                            error = null
                        )
                    )
                }.addOnFailureListener {
                    RecoverPasswordResult(
                        success = false,
                        error = it
                    )
                }
    }
}
