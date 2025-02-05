package com.example.learnsupply.ui.screen.detailsupplier.model

import com.example.learnsupply.model.supplierlist.SupplierFilterData

data class SupplierDetailCallback(
    val onFilter: (SupplierFilterData) -> Unit = {},
    val onSearch: (String) -> Unit = {},
    val onDeleteSupplierById: () -> Unit = {},
    val onResetMessageState: () -> Unit = {},
)
