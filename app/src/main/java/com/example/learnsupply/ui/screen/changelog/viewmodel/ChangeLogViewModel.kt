package com.example.learnsupply.ui.screen.changelog.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiservices.base.Result
import com.example.apiservices.domain.GetChangeLogUseCase
import com.example.learnsupply.ui.screen.changelog.model.ChangeLogCallback
import com.example.learnsupply.ui.screen.changelog.uistate.ChangeLogUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChangeLogViewModel @Inject constructor(
    private val getChangeLogUseCase : GetChangeLogUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(ChangeLogUiState())
    val uiState = _uiState.asStateFlow()

    fun getCallback(): ChangeLogCallback {
        return ChangeLogCallback(

        )
    }

    fun init() {
        getChangeLog()
    }

    private fun getChangeLog() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        Log.d("ChangeLogViewModel", "getChangeLog: called")

        getChangeLogUseCase(_uiState.value.queryParams).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        changeLogDefault = result.data,
                        item = result.data
                    )
                }

                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


}