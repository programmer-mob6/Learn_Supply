package com.example.learnsupply.ui.screen.supplier.view.tab.list.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiservices.base.Result
import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.apiservices.data.source.network.model.request.DeleteSupplierRequestBody
import com.example.apiservices.data.source.network.model.request.PutSupplierIsActiveRequestBody
import com.example.apiservices.domain.DeleteAssetByIdUseCase
import com.example.apiservices.domain.GetSupplierUseCase
import com.example.apiservices.domain.PutIsActiveSupplierUseCase
import com.example.learnsupply.model.supplierlist.SupplierFilterData
import com.example.learnsupply.model.supplierlist.SupplierFilterOption
import com.example.learnsupply.ui.screen.supplier.view.tab.list.model.SupplierListCallback
import com.example.learnsupply.ui.screen.supplier.view.tab.list.uistate.SupplierListUiState
import com.example.learnsupply.util.DataDummy.generateOptionDataString
import com.tagsamurai.tscomponents.utils.ExportUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SupplierListViewModel @Inject constructor(
    private val getSupplierUseCase: GetSupplierUseCase,
    private val deleteAssetByIdUseCase: DeleteAssetByIdUseCase,
    private val putIsActiveSupplierUseCase: PutIsActiveSupplierUseCase,
    private val exportUtil: ExportUtil
) : ViewModel() {
    private val _uiState = MutableStateFlow(SupplierListUiState())
    val uiState = _uiState.asStateFlow()

    fun init() {
        initSupplier()
        getFilterOption()
    }

    fun getCallback(): SupplierListCallback {
        return SupplierListCallback(
            onRefresh = ::initSupplier,
            onFilter = ::updateFilter,
            onSearch = ::search,
            onToggleSelectAll = ::toggleSelectAll,
            onDeleteAssetById = ::deleteAssetById,
            onDownload = ::onDownload,
            onUpdateItemSelected = ::updateItemSelected,
            onResetSelect = ::onResetSelect,
            onUpdateActiveById = ::onUpdateActiveById,
            onShowActionSheet = ::onShowActionSheet,
        )
    }

    private fun onUpdateActiveById(ids: List<String>, status: Boolean) {
        _uiState.value = _uiState.value.copy(isLoadingOverlay = true, isLoading = true)

        val body = PutSupplierIsActiveRequestBody(
            ids = ids,
            isActive = status
        )
        Log.d("SupplierListViewModel 1", "onUpdateActiveById: $body")

        putIsActiveSupplierUseCase(body).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.update { currData ->
                        currData.copy(
                            isLoadingOverlay = false,
                            isLoading = false,
                            itemSelected = emptyList(),
                            activateState = true
                        )
                    }
                    Log.d("SupplierListViewModel 2", "onUpdateActiveById: Success")
                }

                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoadingOverlay = false,
                        isLoading = false,
                        activateState = false
                    )
                    Log.d("SupplierListViewModel 2", "onUpdateActiveById: Error")
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun onShowActionSheet(show: Boolean) {
        _uiState.update { it.copy(showActonSheet = show) }
    }

    fun onResetSelect() {
        _uiState.update { it.copy(itemSelected = emptyList()) }
    }

    private fun updateItemSelected(supplierEntity: SupplierEntity) {
        val selectedItem = _uiState.value.itemSelected.toMutableList()
        _uiState.value = _uiState.value.copy(
            itemSelected = if (selectedItem.contains(supplierEntity)) {
                selectedItem.apply { remove(supplierEntity) }
            } else {
                selectedItem.apply { add(supplierEntity) }
            }
        )
    }

    private fun parseDownloadContent(data: List<SupplierEntity>): List<Map<String, String>> {
        return data.map {
            mapOf(
                "Company Name" to it.companyName,
                "Supplied Item Name" to "${it.suppliedItemName}",
                "Supplied Item Sku" to "${it.suppliedItemSku}",
                "Active Status" to it.isActive,
                "City" to it.city,
                "Country" to it.country,
                "Last Modified" to it.lastModified,
                "PIC" to it.pic,
            )
        }
    }

    private fun onDownload(filename: String) {
        _uiState.update {
            it.copy(
                isLoadingOverlay = true,
                downloadState = null,
            )
        }

        getSupplierUseCase(
            _uiState.value.queryParams
        ).onEach { result ->
            when (result) {
                is Result.Success -> {
                    val downloadContent = parseDownloadContent(result.data)

                    exportUtil.exportToExcel(
                        filename = filename,
                        content = downloadContent,
                    )

                    _uiState.update {
                        it.copy(
                            isLoadingOverlay = false,
                            downloadState = true,
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoadingOverlay = false,
                            downloadState = false,
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteAssetById(id: List<String>) {
        _uiState.value = _uiState.value.copy(isLoadingOverlay = true, isLoading = true)

        Log.d("SupplierListViewModel 1", "deleteAssetById: Called")

        val body = DeleteSupplierRequestBody(
            ids = id
        )

        deleteAssetByIdUseCase(body).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.update { currData ->
                        currData.copy(
                            isLoadingOverlay = false,
                            isLoading = false,
                            itemSelected = emptyList(),
                            deleteState = true
                        )
                    }

                }

                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoadingOverlay = false,
                        isLoading = false,
                        deleteState = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun toggleSelectAll() {
        _uiState.update { currData ->
            currData.copy(
                itemSelected = if (currData.isAllSelected) {
                    emptyList()
                } else {
                    currData.item
                },
                isAllSelected = !currData.isAllSelected
            )
        }
    }


    private fun search(query: String) {
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            filterData = SupplierFilterData()
        )

        initSupplier()
    }

    private fun updateFilter(data: SupplierFilterData) {
        _uiState.value = _uiState.value.copy(
            searchQuery = "",
            filterData = data
        )

        initSupplier()
    }

    private fun getFilterOption() {
        getSupplierUseCase(_uiState.value.queryParams).onEach { result ->
            if (result is Result.Success) {
                _uiState.value = _uiState.value.copy(
                    filterOption = SupplierFilterOption(
                        activeSelected = generateOptionDataString(result.data.map { it.isActive }
                            .distinct()),
                        supplierSelected = generateOptionDataString(result.data.map { it.companyName }
                            .distinct()),
                        citySelected = generateOptionDataString(result.data.map { it.city }
                            .distinct()),
//                        itemNameSelected = generateOptionDataString(result.data.map { it.suppliedItemName }
//                            .distinct()),
                        modifiedBySelected = generateOptionDataString(result.data.map { it.pic }
                            .distinct())
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun initSupplier() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        getSupplierUseCase(_uiState.value.queryParams).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        supplierDefault = result.data,
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