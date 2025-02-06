package com.example.learnsupply.ui.screen.changelog.uistate

import com.example.apiservices.data.model.supplier.ChangeLogEntity
import com.example.apiservices.data.source.network.model.request.GetChangeLogQueryParams
import com.example.learnsupply.model.changelog.ChangeLogFilterData
import com.example.learnsupply.model.changelog.ChangeLogFilterOption
import com.tagsamurai.tscomponents.utils.Utils

data class ChangeLogUiState(
    val isLoading: Boolean = false,
    val isLoadingOverlay: Boolean = false,

    val searchQuery: String = "",
    val filterOption: ChangeLogFilterOption = ChangeLogFilterOption(),
    val filterData: ChangeLogFilterData = ChangeLogFilterData(),

    val downloadState: Boolean? = null,

    val changeLogDefault: List<ChangeLogEntity> = emptyList(),
    val item: List<ChangeLogEntity> = emptyList(),

    ) {
    val queryParams
        get() = GetChangeLogQueryParams(
            search = searchQuery.ifBlank { null },
            action = Utils.toJsonIfNotEmpty(filterData.actionSelected),
            field = Utils.toJsonIfNotEmpty(filterData.fieldSelected),
            modifiedBy = Utils.toJsonIfNotEmpty(filterData.modifiedBySelected),
            date = Utils.toJsonIfNotEmpty(filterData.date),
        )
}
