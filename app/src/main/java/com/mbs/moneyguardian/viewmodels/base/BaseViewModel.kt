package com.mbs.moneyguardian.viewmodels.base

import androidx.lifecycle.ViewModel
import com.mbs.moneyguardian.presentation.uiState.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BaseViewModel<STATE: UiState>(initialState: STATE): ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    val currentUiState get() = _uiState.value



}