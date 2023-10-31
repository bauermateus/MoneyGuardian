package com.mbs.moneyguardian.utils

interface PreferencesDataSource {
    suspend fun saveBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String): Boolean?
}