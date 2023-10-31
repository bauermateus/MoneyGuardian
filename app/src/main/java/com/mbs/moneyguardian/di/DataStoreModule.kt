package com.mbs.moneyguardian.di

import android.app.Application
import com.mbs.moneyguardian.utils.PreferencesDataSource
import com.mbs.moneyguardian.utils.PreferencesDataSourceImpl
import com.mbs.moneyguardian.utils.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun providesPreferencesDataSource(application: Application): PreferencesDataSource {
        return PreferencesDataSourceImpl(application.preferencesDataStore)
    }
}
