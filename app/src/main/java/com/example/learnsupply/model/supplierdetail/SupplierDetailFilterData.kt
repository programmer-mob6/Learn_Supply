package com.example.learnsupply.model.supplierdetail

data class SupplierDetailFilterData(
    val transactionSelected: List<String> = emptyList(),
    val group: List<String> = emptyList(),
    val picSelected: List<String> = emptyList(),
    val date: List<Long> = emptyList()
)
