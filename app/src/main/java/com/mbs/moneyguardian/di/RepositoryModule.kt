package com.mbs.moneyguardian.di

import com.mbs.moneyguardian.data.auth.repository.AuthSignInRepository
import com.mbs.moneyguardian.data.auth.repository.AuthSignInRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun authSignInRepository(authSignInRepositoryImpl: AuthSignInRepositoryImpl): AuthSignInRepository
}