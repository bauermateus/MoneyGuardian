package com.mbs.moneyguardian.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


const val PREFERENCES_DATA_STORE_NAME = "settings"
val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_DATA_STORE_NAME)

/** Class to store app preferences. */
class PreferencesDataSourceImpl(
    private val dataStore: DataStore<Preferences>
): PreferencesDataSource {

    override suspend fun getString(key: String): String? {
        val dataStoreKey = stringPreferencesKey(name = key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    override suspend fun saveString(
        key: String,
        value: String
    ) {
        val dataStoreKey = stringPreferencesKey(name = key)
        dataStore.edit { pref ->
            pref[dataStoreKey] = value
        }
    }

    override suspend fun saveBoolean(key: String, value: Boolean) {
        dataStore.edit { settings ->
            val preferencesKey = booleanPreferencesKey(key)
            settings[preferencesKey] = value
        }
    }

    override suspend fun getBoolean(key: String): Boolean? {
        val preferencesKey = booleanPreferencesKey(key)
        return dataStore.data.map {
            it[preferencesKey]
        }.first()
    }
}


