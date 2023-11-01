package com.mbs.moneyguardian.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mbs.moneyguardian.utils.Constants
import com.mbs.moneyguardian.utils.PreferencesDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val preferences: PreferencesDataSource) :
    ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asLiveData()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asLiveData()

    fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                userName = result.username,
                isSignInSuccessful = result.error == null,
                signInError = result.error
            )
        }
        _loading.update { false }
    }

    fun initLoading() {
        _loading.update { true }
    }

    fun resetState() {
        _state.update { SignInState() }
    }

    suspend fun didUserKeepLogin(): Boolean {
        return preferences.getBoolean(Constants.KEEP_ME_LOGGED) ?: false
    }

}