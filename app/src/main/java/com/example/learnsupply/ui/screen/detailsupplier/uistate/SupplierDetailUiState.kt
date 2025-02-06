package com.example.learnsupply.ui.screen.detailsupplier.uistate

import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.model.supplierdetail.SupplierDetailFilterData
import com.example.learnsupply.model.supplierdetail.SupplierDetailFilterOption
import com.example.learnsupply.ui.screen.detailsupplier.model.DetailSupplierTab

data class SupplierDetailUiState(
    val isLoading: Boolean = false,
    val isLoadingOverlay: Boolean = false,
    val searchQuery: String = "",
    val filterData: SupplierDetailFilterData = SupplierDetailFilterData(),
    val filterOption: SupplierDetailFilterOption = SupplierDetailFilterOption(),

    val deleteState: Boolean? = null,

    val supplierDetail: SupplierEntity = SupplierEntity(),

    val curTabIdx: Int = 0,
) {
    val curTab get() = DetailSupplierTab.entries[curTabIdx]
}
