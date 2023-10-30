package com.mbs.moneyguardian.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class DataStoreManager(
    context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCES)
    private val dataStore = context.dataStore

    suspend fun getString(key: String): String? {
        val dataStoreKey = stringPreferencesKey(name = key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    suspend fun putString(
        key: String,
        value: String
    ) {
        val dataStoreKey = stringPreferencesKey(name = key)
        dataStore.edit { pref ->
            pref[dataStoreKey] = value
        }
    }

    suspend fun getBoolean(key: String): Boolean {
        val dataStoreKey = booleanPreferencesKey(name = key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey] ?: false
    }

    suspend fun putBoolean(
        key: String,
        value: Boolean
    ) {
        val dataStoreKey = booleanPreferencesKey(name = key)
        dataStore.edit { pref ->
            pref[dataStoreKey] = value
        }
    }
}


