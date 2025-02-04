package com.example.learnsupply.ui.screen.supplier.view.tab.list.uistate

import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.apiservices.data.source.network.model.request.GetSupplierQueryParams
import com.example.learnsupply.model.supplierlist.SupplierFilterData
import com.example.learnsupply.model.supplierlist.SupplierFilterOption

data class SupplierListUiState(
    val isLoading: Boolean = false,
    val isLoadingOverlay: Boolean = false,

    val isAllSelected: Boolean = false,
    val itemSelected: List<SupplierEntity> = emptyList(), //AssetEntity
    val searchQuery: String = "",
    val filterData: SupplierFilterData = SupplierFilterData(),
    val filterOption: SupplierFilterOption = SupplierFilterOption(),

    val supplierDefault: List<SupplierEntity> = emptyList(),
    val item: List<SupplierEntity> = emptyList(),
    val downloadState: Boolean? = null,
    val deleteState: Boolean? = null,
    val activateState: Boolean? = null,

    val showActonSheet: Boolean = false

    ) {
    val queryParams
        get() = GetSupplierQueryParams(
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