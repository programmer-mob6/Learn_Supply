package com.example.learnsupply.ui.screen.changelog.model

import com.example.learnsupply.model.supplierlist.SupplierFilterData

data class ChangeLogCallback(
    val onFilter: (SupplierFilterData) -> Unit = {},
    val onSearch: (String) -> Unit = {},
    val onRefresh: () -> Unit = {},

)
