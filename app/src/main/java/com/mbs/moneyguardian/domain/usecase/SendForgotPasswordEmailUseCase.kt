package com.mbs.moneyguardian.domain.usecase

import com.mbs.moneyguardian.data.auth.repository.AuthSignInRepository
import javax.inject.Inject

class SendForgotPasswordEmailUseCase @Inject constructor(
    private val authSignInRepository: AuthSignInRepository
) {
    suspend operator fun invoke(email: String) =
        authSignInRepository.sendRecoverEmail(email)
}