package com.example.learnsupply.model.supplierlist

data class SupplierFilterData(
    val activeSelected: List<Boolean> = emptyList(),
    val supplierSelected: List<String> = emptyList(),
    val citySelected: List<String> = emptyList(),
    val itemNameSelected: List<String> = emptyList(),
    val modifiedBySelected: List<String> = emptyList(),
    val date: List<Long> = emptyList(),
)
