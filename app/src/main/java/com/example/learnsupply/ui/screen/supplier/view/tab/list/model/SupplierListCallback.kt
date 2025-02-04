package com.example.learnsupply.ui.screen.supplier.view.tab.list.model

import com.example.apiservices.data.model.supplier.SupplierEntity
import com.example.learnsupply.model.supplierlist.SupplierFilterData

data class SupplierListCallback(
    val onRefresh: () -> Unit = {},
    val onFilter: (SupplierFilterData) -> Unit = {},
    val onSearch: (String) -> Unit = {},
    val onUpdateItemSelected: (SupplierEntity) -> Unit = {}, //AssetEntity
    val onToggleSelectAll: () -> Unit = {},
    val onDeleteAssetById: (List<String>) -> Unit = {},
    val onUpdateActiveById: (List<String>, Boolean) -> Unit = { _, _ -> },
    val onDownload: (String) -> Unit = {},
    val onResetSelect: () -> Unit = {},

    val onShowActionSheet: (Boolean) -> Unit = {}
)
