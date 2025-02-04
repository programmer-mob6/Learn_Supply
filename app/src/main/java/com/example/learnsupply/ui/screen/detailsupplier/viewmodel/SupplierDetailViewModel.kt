package com.example.learnsupply.ui.screen.detailsupplier.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiservices.base.Result
import com.example.apiservices.data.source.network.model.request.DeleteSupplierRequestBody
import com.example.apiservices.domain.DeleteAssetByIdUseCase
import com.example.apiservices.domain.GetSupplierByIdUseCase
import com.example.learnsupply.navigation.ITEM_ID
import com.example.learnsupply.ui.screen.detailsupplier.model.SupplierDetailCallback
import com.example.learnsupply.ui.screen.detailsupplier.uistate.SupplierDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SupplierDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSupplierByIdUseCase: GetSupplierByIdUseCase,
    private val deleteAssetByIdUseCase: DeleteAssetByIdUseCase,

    ) : ViewModel() {
    private val _uiState = MutableStateFlow(SupplierDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val itemId: String = checkNotNull(savedStateHandle[ITEM_ID])

    fun getCallback(): SupplierDetailCallback {
        return SupplierDetailCallback(
//            onFilter = TODO(),
//            onSearch = TODO(),
            onDeleteSupplierById = ::deleteSupplier
        )
    }

    private fun deleteSupplier(id: List<String>) {
        _uiState.value = _uiState.value.copy(isLoadingOverlay = true)

        val body = DeleteSupplierRequestBody(
            ids = id
        )

        deleteAssetByIdUseCase(body).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.update { currData ->
                        currData.copy(
                            isLoadingOverlay = false,
//                            itemSelected = emptyList(),
//                            deleteState = true
                        )
                    }

//                    initSupplier()
                }

                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoadingOverlay = false,
//                        deleteState = false
                    )
                }
            }
        }.launchIn(viewModelScope)

    }

    fun getDetailSupplier() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        getSupplierByIdUseCase(
            id = itemId
        ).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            supplierDetail = result.data,
                            isLoading = false
                        )
                    }
                }

                else -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun changeCurTab(tab: Int) {
        _uiState.update {
            it.copy(
                curTabIdx = tab,
            )
        }
    }
}