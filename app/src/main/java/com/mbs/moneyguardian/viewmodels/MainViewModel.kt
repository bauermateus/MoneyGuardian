package com.mbs.moneyguardian.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState(false))
    val uiState = _uiState.asLiveData()

    internal fun setState() {
        _uiState.update {
            it.copy(isExpanded = !it.isExpanded)
        }
    }
}