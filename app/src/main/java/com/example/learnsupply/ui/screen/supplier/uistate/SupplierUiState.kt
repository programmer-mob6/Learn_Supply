package com.example.learnsupply.ui.screen.supplier.uistate

import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.model.SupllierTabEnum

data class SupplierUiState(
    val itemSelected: List<SupplierEntity> = emptyList(),

    val downloadState: Boolean? = null,
    val curTabIdx: Int = 0,
) {
    val curTab get() = SupllierTabEnum.entries[curTabIdx]
}