package com.example.learnsupply.ui.screen.supplier.viewmodel

import androidx.lifecycle.ViewModel
import com.example.learnsupply.ui.screen.supplier.uistate.SupplierUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SupplierViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(SupplierUiState())
    val uiState = _uiState.asStateFlow()

    fun changeCurTab(tab: Int) {
        _uiState.update {
            it.copy(
                curTabIdx = tab,
            )
        }
    }
}