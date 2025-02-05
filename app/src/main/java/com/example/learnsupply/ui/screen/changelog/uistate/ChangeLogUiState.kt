package com.example.learnsupply.ui.screen.changelog.uistate

import com.example.apiservices.data.model.supplier.ChangeLogEntity
import com.example.apiservices.data.source.network.model.request.GetChangeLogQueryParams
import com.example.apiservices.data.source.network.model.request.GetSupplierQueryParams

data class ChangeLogUiState(
    val isLoading: Boolean = false,
    val isLoadingOverlay: Boolean = false,

    val searchQuery: String = "",

    val onDownload: (String) -> Unit = {},

    val changeLogDefault: List<ChangeLogEntity> = emptyList(),
    val item: List<ChangeLogEntity> = emptyList(),

) {
    val queryParams
        get() = GetChangeLogQueryParams(
            search = searchQuery.ifBlank { null },
//            active = filterData.activeSelected,
//            supplier = filterData.supplierSelected,
//            city = filterData.citySelected,
//            itemName = filterData.itemNameSelected,
//            itemSku = filterData.itemSkuSelected,
//            modifiedBy = filterData.modifiedBySelected,
//            lastModified = filterData.date,
        )
}
