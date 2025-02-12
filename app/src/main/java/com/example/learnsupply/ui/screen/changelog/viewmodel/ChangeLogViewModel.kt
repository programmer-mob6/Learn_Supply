package com.example.learnsupply.ui.screen.changelog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiservices.base.Result
import com.example.apiservices.data.model.supplier.ChangeLogEntity
import com.example.apiservices.domain.GetChangeLogFilterUseCase
import com.example.apiservices.domain.GetChangeLogUseCase
import com.example.learnsupply.model.changelog.ChangeLogFilterData
import com.example.learnsupply.model.changelog.ChangeLogFilterOption
import com.example.learnsupply.ui.screen.changelog.model.ChangeLogCallback
import com.example.learnsupply.ui.screen.changelog.uistate.ChangeLogUiState
import com.tagsamurai.tscomponents.button.OptionData
import com.tagsamurai.tscomponents.utils.ExportUtil
import com.tagsamurai.tscomponents.utils.Utils.toDateFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChangeLogViewModel @Inject constructor(
    private val getChangeLogUseCase: GetChangeLogUseCase,
    private val getChangeLogFilterUseCase: GetChangeLogFilterUseCase,
    private val exportUtil: ExportUtil
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChangeLogUiState())
    val uiState = _uiState.asStateFlow()

    fun init() {
        _uiState.value = _uiState.value.copy(
            filterData = ChangeLogFilterData()
        )
        getChangeLog()
        getFilterOption()
    }

    fun getCallback(): ChangeLogCallback {
        return ChangeLogCallback(
            onRefresh = ::init,
            onFilter = ::updateFilter,
            onSearch = ::search,
            onDownload = ::onDownload,
            onResetMessageState = ::resetMessageState
        )
    }

    private fun parseDownloadContent(data: List<ChangeLogEntity>): List<Map<String, String>> {
        return data.map {
            mapOf(
                "Action" to it.action,
                "Company Name" to it.companyName,
                "Field" to it.field,
                "New Value" to it.newValue,
                "Old Value" to it.oldValue,
                "Last Modified" to it.lastModified.toDateFormatter(),
                "Modified By" to it.modifiedBy,
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

        getChangeLogUseCase(
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

    private fun updateFilter(data: ChangeLogFilterData) {
        _uiState.value = _uiState.value.copy(
            searchQuery = "",
            filterData = data
        )
        getChangeLog()
    }

    private fun getChangeLog() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        getChangeLogUseCase(_uiState.value.queryParams).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
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

    private fun search(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        getChangeLog()
    }

    private fun getFilterOption() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        getChangeLogFilterUseCase().onEach { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        filterOption = ChangeLogFilterOption(
                            actionSelected = result.data.action.map {
                                OptionData(
                                    label = it.label,
                                    value = it.value
                                )
                            },
                            fieldSelected = result.data.field.map {
                                OptionData(
                                    label = it.label,
                                    value = it.value
                                )
                            },
                            modifiedBySelected = result.data.modifiedBy.map {
                                OptionData(
                                    label = it.label,
                                    value = it.value
                                )
                            }
                        )
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

    private fun resetMessageState() {
        _uiState.value = _uiState.value.copy(
            downloadState = null,
        )
    }
}