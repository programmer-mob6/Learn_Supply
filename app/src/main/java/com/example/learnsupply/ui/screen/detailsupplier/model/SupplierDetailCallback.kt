package com.example.learnsupply.ui.screen.detailsupplier.model

import com.example.learnsupply.model.supplierdetail.SupplierDetailFilterData

data class SupplierDetailCallback(
    val onFilter: (SupplierDetailFilterData) -> Unit = {},
    val onSearch: (String) -> Unit = {},
    val onDeleteSupplierById: () -> Unit = {},
    val onResetMessageState: () -> Unit = {},
)
